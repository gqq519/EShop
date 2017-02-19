package com.feicuiedu.eshop.feature.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.base.BaseActivity;
import com.feicuiedu.eshop.base.utils.LogUtils;
import com.feicuiedu.eshop.base.widgets.SimpleSearchView;
import com.feicuiedu.eshop.base.widgets.loadmore.EndlessScrollListener;
import com.feicuiedu.eshop.base.widgets.loadmore.LoadMoreFooter;
import com.feicuiedu.eshop.base.wrapper.PtrWrapper;
import com.feicuiedu.eshop.base.wrapper.ToastWrapper;
import com.feicuiedu.eshop.base.wrapper.ToolbarWrapper;
import com.feicuiedu.eshop.feature.goods.GoodsActivity;
import com.feicuiedu.eshop.network.EShopClient;
import com.feicuiedu.eshop.network.api.ApiSearch;
import com.feicuiedu.eshop.network.core.ApiPath;
import com.feicuiedu.eshop.network.core.ResponseEntity;
import com.feicuiedu.eshop.network.core.UiCallback;
import com.feicuiedu.eshop.network.entity.Filter;
import com.feicuiedu.eshop.network.entity.Paginated;
import com.feicuiedu.eshop.network.entity.Pagination;
import com.feicuiedu.eshop.network.entity.SearchReq;
import com.feicuiedu.eshop.network.entity.SearchRsp;
import com.feicuiedu.eshop.network.entity.SimpleGoods;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnItemClick;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

public class SearchGoodsActivity extends BaseActivity {

    private static final String EXTRA_SEARCH_FILTER = "EXTRA_SEARCH_FILTER";
    private int mData;
    private LoadMoreFooter mFooter;
    private PtrWrapper mPtrWrapper;

    private Filter mFilter;
    private final Pagination mPagination = new Pagination();

    private Call mSearchCall;
    private long mLastRefreshTime;
    private SearchGoodsAdapter mGoodsAdapter;
    private Paginated mPaginated = new Paginated();


    public static void open(Context context, @Nullable Filter filter) {

        if (filter == null) filter = new Filter();

        Intent intent = new Intent(context, SearchGoodsActivity.class);
        intent.putExtra(EXTRA_SEARCH_FILTER, new Gson().toJson(filter));
        context.startActivity(intent);
    }

    @BindView(R.id.list_goods)
    ListView mGoodsListView;
    @BindView(R.id.search_view)
    SimpleSearchView mSearchView;
    @BindViews({R.id.text_is_hot, R.id.text_most_expensive, R.id.text_cheapest})
    List<TextView> mTvOrderList;

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_search_goods;
    }

    @Override
    protected void initView() {
        new ToolbarWrapper(this);

        // 布局样式
        mTvOrderList.get(0).setActivated(true);

        // 取出数据
        String json = getIntent().getStringExtra(EXTRA_SEARCH_FILTER);
        mFilter = new Gson().fromJson(json, Filter.class);

        // 下拉刷新+上拉加载
        mPtrWrapper = new PtrWrapper(this, true) {
            @Override
            public void onRefresh() {
                // 搜索商品
                searchGoods(true);
            }

            @Override
            public void onLoadMore() {
                // 加载
                if (mPaginated.hasMore()) {
                    searchGoods(false);
                }else {
                    mPtrWrapper.stopRefresh();
                    ToastWrapper.show("无更多数据");
                }

            }
        };

        mSearchView.setOnSearchListener(new SimpleSearchView.OnSearchListener() {
            @Override
            public void search(String text) {
                // 根据输入信息搜索
                mFilter.setKeywords(text);
                mPtrWrapper.autoRefresh();
            }
        });

        // 设置适配器
        mGoodsAdapter = new SearchGoodsAdapter();
        mGoodsListView.setAdapter(mGoodsAdapter);

        mPtrWrapper.postRefreshDelayed(50);
    }

    @OnItemClick(R.id.list_goods)
    public void itemGoodsClick(int position){
        Intent startIntent = GoodsActivity.getStartIntent(this, mGoodsAdapter.getItem(position).getId());
        startActivity(startIntent);
    }


    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity responseEntity) {

        if (!ApiPath.SEARCH.equals(apiPath)){
            throw new UnsupportedOperationException(apiPath);
        }
        mPtrWrapper.stopRefresh();
        mSearchCall = null;
        if (success) {

            SearchRsp searchRsp = (SearchRsp) responseEntity;

            mPaginated = searchRsp.getPaginated();
            List<SimpleGoods> goodsList = searchRsp.getData();
            if (mPagination.isFirst()) {
                mGoodsAdapter.reset(goodsList);
            } else {
                mGoodsAdapter.addAll(goodsList);
            }
        }
    }

    @OnClick({R.id.text_is_hot, R.id.text_most_expensive, R.id.text_cheapest})
    void chooseQueryOrder(TextView textView) {

        if (textView.isActivated()) return;

        if (mPtrWrapper.isRefreshing()) return;

        for (TextView tv : mTvOrderList) {
            tv.setActivated(false);
        }
        textView.setActivated(true);

        String sortBy;

        switch (textView.getId()) {
            case R.id.text_is_hot:
                sortBy = Filter.SORT_IS_HOT;
                break;
            case R.id.text_most_expensive:
                sortBy = Filter.SORT_PRICE_DESC;
                break;
            case R.id.text_cheapest:
                sortBy = Filter.SORT_PRICE_ASC;
                break;
            default:
                throw new UnsupportedOperationException();
        }

        mFilter.setSortBy(sortBy);
        mPtrWrapper.autoRefresh();
    }

    // 搜索商品
    private void searchGoods(final boolean isRefresh) {

        if (mSearchCall != null) {
            mSearchCall.cancel();
        }

        if (isRefresh) {
            mPagination.reset();
        } else {
            mPagination.next();
            LogUtils.debug("Load more, page = %s", mPagination.getPage());
        }
        ApiSearch apiSearch = new ApiSearch(mFilter,mPagination);
        mSearchCall = enqueue(apiSearch);
    }

    private UiCallback mUiCallback = new UiCallback() {
        @Override
        public void onBusinessResponse(boolean success, ResponseEntity responseEntity) {
            // 拿到数据处理
            mPtrWrapper.stopRefresh();
            mSearchCall = null;
            if (success) {

                SearchRsp searchRsp = (SearchRsp) responseEntity;

                mPaginated = searchRsp.getPaginated();
                List<SimpleGoods> goodsList = searchRsp.getData();
                if (mPagination.isFirst()) {
                    mGoodsAdapter.reset(goodsList);
                } else {
                    mGoodsAdapter.addAll(goodsList);
                }
            }
        }
    };
}
