package com.feicuiedu.eshop.base.widgets.loadmore;

/**
 * Created by gqq on 2017/2/15.
 */


import android.widget.AbsListView;

import com.feicuiedu.eshop.base.utils.LogUtils;

/**
 * 用于分页加载的ListView滚动监听器.
 */

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    private static final int EMPTY_ITEM_COUNT = 1;
    private int mVisibleThreshold = 0; // 触发加载更多的临界位置

    // 上次加载后的总项数 (注意由于ListView的Footer, 初始为1)
    private int mPreviousTotalItemCount = EMPTY_ITEM_COUNT;

    private boolean mLoading = true; // true代表正在等待数据加载.

    public EndlessScrollListener(int visibleThreshold) {
        mVisibleThreshold = visibleThreshold;
    }

    /**
     *滚动状态发生变化
     * @param view  当前滚动状态改变的视图
     * @param scrollState  新的滚动状态(滚动中或停止)
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     *正在滚动
     * @param view 当前正在滚动的视图(ListView或GridView)
     * @param firstVisibleItem 首个可见子项的index
     * @param visibleItemCount 可见子项的总数
     * @param totalItemCount 适配器中的子项的总数
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        // 如果总项数为空值, 但之前记录的总项数不是, 则代表数据被重置.
        if (totalItemCount<mPreviousTotalItemCount){
            this.mPreviousTotalItemCount = totalItemCount;
            if (totalItemCount==EMPTY_ITEM_COUNT){
                this.mLoading = true;
            }
        }

        // 如果正在加载, 检查数据是否改变.
        // 如果数据改变, 代表加载结束.
        boolean dataChanged = totalItemCount > mPreviousTotalItemCount;
        if (mLoading && dataChanged) {
            LogUtils.debug("dataChanged mPreviousTotalItemCount %d, totalItemCount %d",
                    mPreviousTotalItemCount, totalItemCount);
            mLoading = false;
            mPreviousTotalItemCount = totalItemCount;
        }

        // 如果当前没有加载, 检查是否达到了触发加载的临界条件.
        // 如果条件满足, 触发onLoadMore()方法.
        boolean needReload =
                (firstVisibleItem + visibleItemCount + mVisibleThreshold) >= totalItemCount;
        if (!mLoading && needReload) {
            mLoading = onLoadMore();
            LogUtils.debug("EndlessScrollListener onLoadMore %b, totalItemCount %d, ",
                    mLoading, totalItemCount);
        }

    }

    /**
     * 子类重写此方法, 实现加载更多数据的过程.
     *
     * @return 如果有更多数据需要加载, 返回true; 否则返回false.
     */
    protected abstract boolean onLoadMore();
}
