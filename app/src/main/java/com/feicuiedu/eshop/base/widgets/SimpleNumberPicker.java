package com.feicuiedu.eshop.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicuiedu.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gqq on 17/2/19.
 */

// 自定义数量选择器
public class SimpleNumberPicker extends RelativeLayout {

    @BindView(R.id.image_minus)
    ImageView mIvMinus;
    @BindView(R.id.text_number)
    TextView mTvNumber;
    @BindView(R.id.image_plus)
    ImageView mIvPlus;

    private OnNumberChangedListener mChangedListener;

    private int mMin = 0;

    public SimpleNumberPicker(Context context) {
        super(context);
        init(context);
    }

    public SimpleNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_simple_number_picker, this, true);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.image_minus, R.id.image_plus})
    public void onClick(View view) {
        if (view.getId()==R.id.image_minus){
            if (getNumber()==mMin){
                return;
            }
            setNumber(getNumber()-1);
        }else {
            setNumber(getNumber()+1);
        }

        if (getNumber()==mMin){
            mIvMinus.setImageResource(R.drawable.btn_minus_gray);
        }else {
            mIvMinus.setImageResource(R.drawable.btn_minus);
        }
        // 数量改变了，弹窗上面的数量也要发生变化，如何传递，利用接口回调
        if (mChangedListener!=null){
            mChangedListener.onNumberChanged(getNumber());
        }
    }

    public int getNumber() {
        String str = mTvNumber.getText().toString();
        return Integer.parseInt(str);
    }

    public void setNumber(int number) {

        if (number < mMin) {
            throw new IllegalArgumentException("Min is " + mMin + " while number is " + number);
        }

        mTvNumber.setText(String.valueOf(number));
    }

    public void setOnNumberChangedListener(OnNumberChangedListener onNumberChangedListener){
        this.mChangedListener = onNumberChangedListener;
    }

    public interface OnNumberChangedListener{
        void onNumberChanged(int number);
    }
}
