package com.feicuiedu.eshop.feature;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.base.BaseActivity;
import com.feicuiedu.eshop.base.TestFragment;
import com.feicuiedu.eshop.feature.category.CategoryFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import junit.framework.Test;

import butterknife.BindView;
import butterknife.ButterKnife;

// 主页面

/**
 * 1. 搭建布局：说明BottomBar的用法：在github上搜索，添加依赖、在布局中加入
 * 2. 分析切换Fragment，显示不同的模块标题，创建Fragment
 * 3. 功能实现：
 *  3.1 initView()里面添加bottombar的监听
 *  3.2 重写的监听的方法中设置switch
 *  3.3 创建不同模块Fragment实例
 *  3.4 实现切换方法
 *  3.5 解决可能出现的Fragment重叠问题
 *  3.6 返回键处理
 */

public class EShopMainActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    private TestFragment mHomeFragment;
    private CategoryFragment mCategroyFragment;
    private TestFragment mCartFragment;
    private TestFragment mMineFragment;

    private Fragment mCurrentFragment;

    // 基类重写的方法
    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_eshop_main;
    }

    @Override
    protected void initView() {
        // 找回FragmentManager中存储的Fragment
        retrieveFragment();
        // 设置切换监听
        mBottomBar.setOnTabSelectListener(this);
    }


    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_home:
                if (mHomeFragment == null) {
                    mHomeFragment = TestFragment.getInstance("首页");
                }
                // 切换
                switchFragment(mHomeFragment);
                break;
            case R.id.tab_category:
                if (mCategroyFragment == null) {
                    mCategroyFragment = new CategoryFragment();
                }
                // 切换
                switchFragment(mCategroyFragment);
                break;
            case R.id.tab_cart:
                if (mCartFragment == null) {
                    mCartFragment = TestFragment.getInstance("购物车");
                }
                // 切换
                switchFragment(mCartFragment);
                break;
            case R.id.tab_mine:
                if (mMineFragment == null) {
                    mMineFragment = TestFragment.getInstance("我的");
                }
                // 切换
                switchFragment(mMineFragment);
                break;
        }
    }

    // 切换Fragment
    private void switchFragment(Fragment target) {
        if (mCurrentFragment==target) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mCurrentFragment!=null){
            // 隐藏当前显示的Fragment
            transaction.hide(mCurrentFragment);
        }

        if (target.isAdded()){
            // 如果目标已被添加到FragmentManager中，就显示
            transaction.show(target);
        }else {
            // 否则直接添加
            String tag;

//            前期页面搭建不需要判断
//            tag = ((TestFragment)target).getArgumentText();
//            transaction.add(R.id.layout_container,target,tag);

//            后期更新不全是TestFragment时，需要判断
            if (target instanceof TestFragment) {
                tag = ((TestFragment) target).getArgumentText();
            } else {
                tag = target.getClass().getName();
            }
            transaction.add(R.id.layout_container,target,tag);
        }

        transaction.commit();
        mCurrentFragment=target;
    }

    // 找回FragmentManager中存储的Fragment
    private void retrieveFragment() {
        FragmentManager manager = getSupportFragmentManager();
        mHomeFragment = (TestFragment) manager.findFragmentByTag("首页");
        mCategroyFragment = (CategoryFragment) manager.findFragmentByTag(CategoryFragment.class.getName());
        mCartFragment = (TestFragment) manager.findFragmentByTag("购物车");
        mMineFragment = (TestFragment) manager.findFragmentByTag("我的");
    }

    // 处理返回键
    @Override
    public void onBackPressed() {
        if (mCurrentFragment!=mHomeFragment){
            mBottomBar.selectTabWithId(R.id.tab_home);
            return;
        }

//        用于将activity退到后台，不是finish
        moveTaskToBack(true);
    }
}
