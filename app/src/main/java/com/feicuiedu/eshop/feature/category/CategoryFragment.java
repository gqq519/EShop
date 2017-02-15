package com.feicuiedu.eshop.feature.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.base.BaseFragment;
import com.feicuiedu.eshop.base.utils.LogUtils;
import com.feicuiedu.eshop.base.wrapper.ToolbarWrapper;
import com.feicuiedu.eshop.feature.EShopMainActivity;
import com.feicuiedu.eshop.feature.search.SearchGoodsActivity;
import com.feicuiedu.eshop.network.EShopClient;
import com.feicuiedu.eshop.network.core.ResponseEntity;
import com.feicuiedu.eshop.network.core.UiCallback;
import com.feicuiedu.eshop.network.entity.CategoryPrimary;
import com.feicuiedu.eshop.network.entity.CategoryRsp;
import com.feicuiedu.eshop.network.entity.Filter;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by gqq on 2017/2/10.
 */

// 商品分类页面

public class CategoryFragment extends BaseFragment {

    @BindView(R.id.standard_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.standard_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list_category)
    ListView mListCategory;
    @BindView(R.id.list_children)
    ListView mListChildren;

    private List<CategoryPrimary> mData;
    private CategoryAdapter mCategoryAdapter;
    private ChildrenAdapter mChildrenAdapter;
    private Handler mHandler = new Handler();

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_category;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_category, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // 跳转到搜索页面
            case R.id.menu_search:

                int position = mListCategory.getCheckedItemPosition();
                int categoryId = mCategoryAdapter.getItem(position).getId();
                navigateToSearch(categoryId);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {

        // toolbar
        new ToolbarWrapper(this).setCustomTitle(R.string.category_title);

        // 设置适配器：创建及实现
        mCategoryAdapter = new CategoryAdapter();
        mListCategory.setAdapter(mCategoryAdapter);

        mChildrenAdapter = new ChildrenAdapter();
        mListChildren.setAdapter(mChildrenAdapter);

        if (mData != null) {
            updateCategory();
        } else {
            // 执行网络请求的操作
            enqueue();
        }
    }

    // 执行网络请求
    private void enqueue() {
        UiCallback uiCallback = new UiCallback() {
            @Override
            public void onBusinessResponse(boolean success, ResponseEntity responseEntity) {
                if (success) {
                    mData = ((CategoryRsp) responseEntity).getData();
                    updateCategory();
                }
            }
        };
        EShopClient.getInstance().enqueue("/category", null, CategoryRsp.class, uiCallback);
    }

    // 更新分类信息
    private void updateCategory() {
        mCategoryAdapter.reset(mData);
        chooseCategory(0);
    }

    // 切换一级展示的二级分类的内容
    private void chooseCategory(int position) {
        mListCategory.setItemChecked(position, true);
        mChildrenAdapter.reset(mData.get(position).getChildren());
    }

    @OnItemClick(R.id.list_category)
    public void onItemClick(int position) {
        chooseCategory(position);
    }

    @OnItemClick(R.id.list_children)
    public void onChildItemClick(int position) {
        navigateToSearch(mChildrenAdapter.getItem(position).getId());
    }

    private void navigateToSearch(int categoryId) {
        Filter filter = new Filter();
        filter.setCategoryId(categoryId);
        SearchGoodsActivity.open(getContext(), filter);
    }
}
