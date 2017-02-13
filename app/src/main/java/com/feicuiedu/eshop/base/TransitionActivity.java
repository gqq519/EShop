package com.feicuiedu.eshop.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.feicuiedu.eshop.R;

/**
 * Created by gqq on 2017/2/13.
 */

// 页面跳转动画封装的Activity
public class TransitionActivity extends AppCompatActivity{

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        setTransitionAnimation(true);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        setTransitionAnimation(true);
    }

    @Override
    public void finish() {
        super.finish();
        setTransitionAnimation(false);
    }

    public void setTransitionAnimation(boolean newActivityIn) {
        if (newActivityIn){
            // 新页面从右边进入
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }else {
            // 返回上个页面:从左边进入
            overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
        }
    }
}
