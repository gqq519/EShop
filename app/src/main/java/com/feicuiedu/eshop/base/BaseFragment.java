package com.feicuiedu.eshop.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.eshop.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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



    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mUnbinder = null;
    }

    @LayoutRes
    protected abstract int getContentViewLayout();

    protected abstract void initView();
}
