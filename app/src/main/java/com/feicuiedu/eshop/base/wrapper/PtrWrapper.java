package com.feicuiedu.eshop.base.wrapper;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.feicuiedu.eshop.R;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by gqq on 2017/2/14.
 */

// 下拉刷新的包装类
public abstract class PtrWrapper {

    /**
     * 1. 构造方法：Activity/Fragment中使用及初始化
     * 2. 延时自动刷新方法
     * 3. 自动刷新
     * 4. 停止刷新
     * 5. 刷新获取数据：包装类，获取数据未知，无法处理，所以要求实现类处理(抽象的方法)
     * 6. 是不是正在刷新
     */

    private PtrFrameLayout mRefreshLayout;

    private PtrHandler mPtrHandler = new PtrDefaultHandler2() {
        @Override
        public void onLoadMoreBegin(PtrFrameLayout frame) {
            onLoadMore();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            onRefresh();
        }
    };

    // Activity
    public PtrWrapper(Activity activity,boolean isNeedLoad) {
        mRefreshLayout = ButterKnife.findById(activity, R.id.standard_refresh_layout);
        initPtr(isNeedLoad);
    }

    // Fragment
    public PtrWrapper(Fragment fragment,boolean isNeedLoad) {
        mRefreshLayout = ButterKnife.findById(fragment.getView(),R.id.standard_refresh_layout);
        initPtr(isNeedLoad);
    }

    // 初始化Ptr样式
    private void initPtr(boolean isNeedLoad) {
        if (mRefreshLayout != null) {
            mRefreshLayout.disableWhenHorizontalMove(true);

            initPtrHeader();

            if (isNeedLoad){
                initPtrFooter();
            }

            mRefreshLayout.setPtrHandler(mPtrHandler);
        }
    }

    // 头布局
    private void initPtrHeader() {
        PtrClassicDefaultHeader refreshHeader =
                new PtrClassicDefaultHeader(mRefreshLayout.getContext());
        mRefreshLayout.setHeaderView(refreshHeader);
        mRefreshLayout.addPtrUIHandler(refreshHeader);
    }

    // 加载布局
    private void initPtrFooter() {
        PtrClassicDefaultFooter refreshFooter =
                new PtrClassicDefaultFooter(mRefreshLayout.getContext());
        mRefreshLayout.setFooterView(refreshFooter);
        mRefreshLayout.addPtrUIHandler(refreshFooter);
    }



    // 自动刷新
    public void autoRefresh(){
        mRefreshLayout.autoRefresh();
    }

    // 延时自动刷新
    public void postRefreshDelayed(long delay){
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.autoRefresh();
            }
        },delay);
    }

    // 停止刷新
    public void stopRefresh(){
        if (mRefreshLayout.isRefreshing()){

            mRefreshLayout.refreshComplete();
        }
    }

    // 是不是正在刷新
    public boolean isRefreshing(){
        return mRefreshLayout.isRefreshing();
    }

    // 去刷新数据
    public abstract void onRefresh();

    // 去加载数据
    public abstract void onLoadMore();

}
