package com.feicuiedu.eshop.feature;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements Animator.AnimatorListener {

    @BindView(R.id.image_splash)
    ImageView mIvSplash;

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

        // 首先设置透明度
        mIvSplash.setAlpha(0.3f);

        // 设置一个渐变的效果:结束的透明度、持续时间、动画监听
        /**
         * 动画采用ViewPropertyAnimator来实现：
         * ViewPropertyAnimator专门针对View对象动画而操作的类。
         * 1. 提供简洁的链式调用多个动画效果，动画可以同时进行
         * 2. 多个属性同时变化，只刷新一个UI，性能更加优化。
         * 3.该类只能通过View的animate()获取其实例对象的引用
         */
        mIvSplash.animate()
                .alpha(1.0f)
                .setDuration(2000)
                .setListener(this)
                .start();
    }

    // 动画开始
    @Override
    public void onAnimationStart(Animator animator) {

    }

    // 动画结束
    @Override
    public void onAnimationEnd(Animator animator) {
        Intent intent = new Intent(this,EShopMainActivity.class);
        startActivity(intent);
        finish();
        // 设置转场动画
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    // 动画被取消
    @Override
    public void onAnimationCancel(Animator animator) {

    }

    // 动画重复播放
    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
