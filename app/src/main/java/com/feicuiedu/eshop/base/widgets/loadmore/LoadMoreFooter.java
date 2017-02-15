package com.feicuiedu.eshop.base.widgets.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feicuiedu.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gqq on 2017/2/15.
 */

// 加载更多的布局
public class LoadMoreFooter extends FrameLayout {

    public static final int STATE_LOADED = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_COMPLETE = 2;
    @BindView(R.id.text_load_complete)
    TextView mTvComplete;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public LoadMoreFooter(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_load_more_footer, this);
        ButterKnife.bind(this);
        setState(STATE_LOADED);
    }

    public void setState(int state) {
        switch (state) {
            case STATE_LOADED:
                setVisibility(View.GONE);
                break;
            case STATE_LOADING:
                setVisibility(View.VISIBLE);
                mTvComplete.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                setVisibility(View.VISIBLE);
                mTvComplete.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
                break;
            default:
                throw new IllegalStateException("Illegal state: " + state);
        }
    }
}
