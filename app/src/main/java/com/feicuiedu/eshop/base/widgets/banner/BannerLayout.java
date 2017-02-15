package com.feicuiedu.eshop.base.widgets.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.feicuiedu.eshop.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by gqq on 2017/2/14.
 */

// 轮播图的组合控件
public class BannerLayout extends RelativeLayout {

    @BindView(R.id.pager_banner)
    ViewPager mPagerBanner;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    private Timer mCycleTimer;
    private TimerTask mCycleTask;
    private long mDuration = 4000;
    private CyclingHandler mCyclingHandler;
    private long mResumeCyclingTime = 0;

    public BannerLayout(Context context) {
        super(context);
        init(context);
    }


    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_banner_layout, this, true);
        ButterKnife.bind(this);
        mCyclingHandler = new CyclingHandler(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // 计时器定时发送消息进行轮播
        mCycleTimer = new Timer();
        mCycleTask = new TimerTask() {
            @Override
            public void run() {
                mCyclingHandler.sendEmptyMessage(0);
            }
        };

        mCycleTimer.schedule(mCycleTask, mDuration, mDuration);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        // 计时器取消、对象置空等
        mCycleTimer.cancel();
        mCycleTask.cancel();
        mCycleTask = null;
        mCycleTimer = null;
    }

    // 切换到下一页
    public void moveToNextPosition() {

        // 未设置适配器
        if (mPagerBanner.getAdapter() == null) {
            throw new IllegalStateException("You did not set a banner adapter");
        }

        int count = mPagerBanner.getAdapter().getCount();

        //适配器无数据
        if (count == 0) return;

        // 当前在最后一页
        if (mPagerBanner.getCurrentItem() == count - 1) {
            mPagerBanner.setCurrentItem(0, false);
        } else {
            mPagerBanner.setCurrentItem(mPagerBanner.getCurrentItem() + 1, true);
        }
    }

    // 设置适配器
    public void setAdapter(BannerAdapter adapter) {
        // Viewpager和指示器的关联
        // Please change like this if you used dynamic adapter
        mPagerBanner.setAdapter(adapter);
        mIndicator.setViewPager(mPagerBanner);
        adapter.registerDataSetObserver(mIndicator.getDataSetObserver());
    }

    // 处理手动切换和自动切换的冲突
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mResumeCyclingTime = System.currentTimeMillis()+mDuration;
        return super.dispatchTouchEvent(ev);
    }

    private static class CyclingHandler extends Handler {

        // 非静态内部类的Handler可能引起内存泄漏, 使用静态内部类 + 弱引用的形式, 可以避免此问题.
        private WeakReference<BannerLayout> mBannerReference;

        public CyclingHandler(BannerLayout bannerLayout) {
            mBannerReference = new WeakReference<BannerLayout>(bannerLayout);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mBannerReference == null) return;

            BannerLayout bannerLayout = mBannerReference.get();

            if (bannerLayout == null) return;

            if (System.currentTimeMillis()<bannerLayout.mResumeCyclingTime){
                return;
            }

            //让BannerLayout移动到下一页
            bannerLayout.moveToNextPosition();
        }
    }
}
