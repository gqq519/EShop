package com.feicuiedu.eshop.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by gqq on 2017/2/13.
 */

// 通用Activity的基类
public abstract class BaseActivity extends TransitionActivity {

    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 缺少布局，既然是基类，就让子类传递布局
        setContentView(getContentViewLayout());
        mUnbinder = ButterKnife.bind(this);

        // 比如创建一个方法初始化视图相关，也做一下封装。
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mUnbinder = null;
    }

    // 设置Activity的布局资源
    @LayoutRes
    protected abstract int getContentViewLayout();

    // 在此方法中执行视图的初始化
    protected abstract void initView();


}
