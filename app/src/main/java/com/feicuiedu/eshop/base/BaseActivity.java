package com.feicuiedu.eshop.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;

import com.feicuiedu.eshop.network.EShopClient;
import com.feicuiedu.eshop.network.core.ApiInterface;
import com.feicuiedu.eshop.network.core.ResponseEntity;
import com.feicuiedu.eshop.network.core.UiCallback;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

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

        // 取消请求
        EShopClient.getInstance().cancleByTag(getClass().getSimpleName());
        mUnbinder.unbind();
        mUnbinder = null;
    }

    // 提供一个请求方法
    protected Call enqueue(final ApiInterface apiInterface){

        UiCallback uiCallback = new UiCallback() {
            @Override
            public void onBusinessResponse(boolean success, ResponseEntity responseEntity) {
                BaseActivity.this.onBusinessResponse(apiInterface.getPath(),success,responseEntity);
            }
        };

        return EShopClient.getInstance().enqueue(apiInterface,uiCallback,getClass().getSimpleName());
    }

    // 设置Activity的布局资源
    @LayoutRes
    protected abstract int getContentViewLayout();

    // 在此方法中执行视图的初始化
    protected abstract void initView();

    // 将数据返回出去
    protected abstract void onBusinessResponse(String apiPath, boolean success, ResponseEntity responseEntity);


}
