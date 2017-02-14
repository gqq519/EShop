package com.feicuiedu.eshop.base.wrapper;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.base.BaseActivity;
import com.feicuiedu.eshop.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by gqq on 2017/2/14.
 */

// Toolbar的包装类
public class ToolbarWrapper {

    private BaseActivity mActivity;
    private TextView mTvTitle;

    // 构造方法：Activity
    public ToolbarWrapper(BaseActivity activity) {

        mActivity = activity;
        /**
         * 1. 找到toolbar
         * 2. 找到TextView
         * 3. toolbar作为ActionBar展示
         * 4. 显示标题、返回键
         */
        Toolbar toolbar = ButterKnife.findById(activity, R.id.standard_toolbar);
        init(toolbar);

        // 返回箭头和标题
        setShowBack(true);
        setShowTitle(false);

    }

    private void init(Toolbar toolbar) {
        mTvTitle = ButterKnife.findById(toolbar, R.id.standard_toolbar_title);
        mActivity.setSupportActionBar(toolbar);
    }

    public ToolbarWrapper(BaseFragment fragment) {
        mActivity = (BaseActivity) fragment.getActivity();
        Toolbar toolbar = ButterKnife.findById(fragment.getView(), R.id.standard_toolbar);
        init(toolbar);
        fragment.setHasOptionsMenu(true);// Fragment设置有选项菜单
        // 返回箭头和标题
        setShowBack(false);
        setShowTitle(false);

    }

    public ToolbarWrapper setShowBack(boolean showBack) {
        // 返回本类，可以实现链式调用，提供一个方法拿到ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(showBack);
        return this;
    }

    public ToolbarWrapper setShowTitle(boolean showTitle) {
        getSupportActionBar().setDisplayShowTitleEnabled(showTitle);
        return this;
    }

    public ToolbarWrapper setCustomTitle(int resId){

        if (mTvTitle==null){
            throw new UnsupportedOperationException("No title TextView in Toolbar");
        }
        mTvTitle.setText(resId);
        return this;
    }

    public ActionBar getSupportActionBar() {
        return mActivity.getSupportActionBar();
    }
}
