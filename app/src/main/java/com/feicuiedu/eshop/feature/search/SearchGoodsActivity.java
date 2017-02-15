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
import com.feicuiedu.eshop.base.widgets.SimpleSearchView;
import com.feicuiedu.eshop.base.widgets.loadmore.EndlessScrollListener;
import com.feicuiedu.eshop.base.widgets.loadmore.LoadMoreFooter;
import com.feicuiedu.eshop.base.wrapper.ToastWrapper;
import com.feicuiedu.eshop.base.wrapper.ToolbarWrapper;
import com.feicuiedu.eshop.network.entity.Filter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

public class SearchGoodsActivity extends BaseActivity {

    private static final String EXTRA_SEARCH_FILTER = "EXTRA_SEARCH_FILTER";
    private int mData;
    private LoadMoreFooter mFooter;

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

    private int mIndex;

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_search_goods;
    }

    @Override
    protected void initView() {
        new ToolbarWrapper(this);

        mSearchView.setOnSearchListener(new SimpleSearchView.OnSearchListener() {
            @Override
            public void search(String text) {
                ToastWrapper.show(text);
            }
        });

        final ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, getData());
        mGoodsListView.setAdapter(adapter);

        mFooter = new LoadMoreFooter(this);
        mGoodsListView.addFooterView(mFooter);
        mGoodsListView.setOnScrollListener(new EndlessScrollListener(1) {
            @Override
            protected boolean onLoadMore() {
                if (mIndex >= 100) {
                    mFooter.setState(LoadMoreFooter.STATE_COMPLETE);
                    return false;
                } else {
                    mFooter.setState(LoadMoreFooter.STATE_LOADING);
                    mGoodsListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addAll(getData());
                            mFooter.setState(LoadMoreFooter.STATE_LOADED);
                        }
                    }, 3000);
                    return true;
                }
            }
        });
    }


    private List<Integer> getData() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(mIndex++);
        }
        return data;
    }
}
