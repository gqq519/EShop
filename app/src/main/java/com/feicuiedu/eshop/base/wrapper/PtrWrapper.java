package com.feicuiedu.eshop.base.wrapper;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.feicuiedu.eshop.R;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
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
     */

    private PtrFrameLayout mRefreshLayout;

    private PtrHandler mPtrHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            // 刷新，去获取数据
            onRefresh();
        }
    };

    // Activity
    public PtrWrapper(Activity activity) {
        mRefreshLayout = ButterKnife.findById(activity, R.id.standard_refresh_layout);
        initPtr();
    }

    // Fragment
    public PtrWrapper(Fragment fragment) {
        mRefreshLayout = ButterKnife.findById(fragment.getView(),R.id.standard_refresh_layout);
        initPtr();
    }

    // 初始化Ptr样式
    private void initPtr() {
        if (mRefreshLayout != null) {
            mRefreshLayout.disableWhenHorizontalMove(true);
            PtrClassicDefaultHeader refreshHeader =
                    new PtrClassicDefaultHeader(mRefreshLayout.getContext());
            mRefreshLayout.setHeaderView(refreshHeader);
            mRefreshLayout.addPtrUIHandler(refreshHeader);
            mRefreshLayout.setPtrHandler(mPtrHandler);
        }
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

    // 去刷新数据
    public abstract void onRefresh();
}
