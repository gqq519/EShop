package com.feicuiedu.eshop.feature.goods;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.base.BaseActivity;
import com.feicuiedu.eshop.base.widgets.SimpleNumberPicker;
import com.feicuiedu.eshop.base.wrapper.ToastWrapper;
import com.feicuiedu.eshop.network.entity.GoodsInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gqq on 17/2/19.
 */

public class GoodsSpecPopupWindow extends PopupWindow implements PopupWindow.OnDismissListener {

    @BindView(R.id.image_goods)
    ImageView mIvGoods;
    @BindView(R.id.text_goods_price)
    TextView mTvPrice;
    @BindView(R.id.text_number_value)
    TextView mTvNumber;
    @BindView(R.id.text_inventory_value)
    TextView mTvInventory;
    @BindView(R.id.number_picker)
    SimpleNumberPicker mNumberPicker;

    private final ViewGroup mParent;
    private GoodsInfo mGoodsInfo;
    private BaseActivity mActivity;

    private OnConfirmListener mConfirmListener;

    // 构造方法里面初始化布局等
    public GoodsSpecPopupWindow(BaseActivity activity, GoodsInfo goodsInfo) {
        mGoodsInfo = goodsInfo;
        this.mActivity = activity;

        mParent = (ViewGroup) activity.getWindow().getDecorView();

        View view = LayoutInflater.from(activity).inflate(R.layout.popup_goods_spec, mParent, false);
        setContentView(view);

        // 设置宽高
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(mParent.getContext().getResources().getDimensionPixelSize(R.dimen.size_400));

        setFocusable(true);
        // 必须设置
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);

        // 该Activity总是调整屏幕的大小以便留出软键盘的空间
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setOnDismissListener(this);

        ButterKnife.bind(this, view);
        initView();

    }

    private void initView() {
        // 初始化视图
        Picasso.with(mParent.getContext()).load(mGoodsInfo.getImg().getLarge()).into(mIvGoods);

        mTvPrice.setText(mGoodsInfo.getShopPrice());
        mTvInventory.setText(String.valueOf(mGoodsInfo.getNumber()));
        mNumberPicker.setOnNumberChangedListener(new SimpleNumberPicker.OnNumberChangedListener() {
            @Override
            public void onNumberChanged(int number) {
                mTvNumber.setText(String.valueOf(number));
            }
        });
    }

    public void show(OnConfirmListener onConfirmListener) {
        this.mConfirmListener = onConfirmListener;
        showAtLocation(mParent, Gravity.BOTTOM,0,0);
        //显示设置背景
        backgroundAlpha(0.6f);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1.0f);
        mConfirmListener = null;
    }

    @OnClick(R.id.button_ok)
    public void onClick() {

        // 确定按钮，要将选择的数量传递出去，接口回调的方式。
        int number = mNumberPicker.getNumber();
        if (number==0){
            ToastWrapper.show(R.string.goods_msg_must_choose_number);
            return;
        }

        mConfirmListener.onConfirm(number);
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }

    public interface OnConfirmListener{
        void onConfirm(int number);
    }
}
