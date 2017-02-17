package com.feicuiedu.eshop.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.eshop.R;
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

// 通用的Fragment基类
public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewLayout(),container,false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    // 提供一个请求方法
    protected Call enqueue(final ApiInterface apiInterface){

        UiCallback uiCallback = new UiCallback() {
            @Override
            public void onBusinessResponse(boolean success, ResponseEntity responseEntity) {
                BaseFragment.this.onBusinessResponse(apiInterface.getPath(),success,responseEntity);
            }
        };

        return EShopClient.getInstance().enqueue(apiInterface,uiCallback,getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取消请求
        EShopClient.getInstance().cancleByTag(getClass().getSimpleName());
        mUnbinder.unbind();
        mUnbinder = null;
    }

    @LayoutRes
    protected abstract int getContentViewLayout();

    protected abstract void initView();

    // 将数据返回出去
    protected abstract void onBusinessResponse(String apiPath, boolean success, ResponseEntity responseEntity);

}
