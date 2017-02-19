package com.feicuiedu.eshop.feature.goods.info;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.base.BaseFragment;
import com.feicuiedu.eshop.base.wrapper.ToastWrapper;
import com.feicuiedu.eshop.feature.goods.GoodsActivity;
import com.feicuiedu.eshop.network.core.ResponseEntity;
import com.feicuiedu.eshop.network.entity.GoodsInfo;
import com.feicuiedu.eshop.network.entity.Picture;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by gqq on 17/2/19.
 */

public class GoodsInfoFragment extends BaseFragment {

    private static final String ARGUMENT_GOODS_INFO = "ARGUMENT_GOODS_INFO";
    @BindView(R.id.pager_goods_pictures)
    ViewPager mPicturePager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    @BindView(R.id.button_favorite)
    ImageButton mBtnFavorite;
    @BindView(R.id.text_goods_name)
    TextView mTvGoodsName;
    @BindView(R.id.text_goods_price)
    TextView mTvGoodsPrice;
    @BindView(R.id.text_market_price)
    TextView mTvMarketPrice;
    private GoodsInfo mGoodsInfo;

    /**
     * @param goodsInfo 待显示的商品信息实体
     * @return 新的GoodsInfoFragment对象
     */
    public static GoodsInfoFragment newInstance(GoodsInfo goodsInfo) {

        Bundle args = new Bundle();
        args.putString(ARGUMENT_GOODS_INFO, new Gson().toJson(goodsInfo));

        GoodsInfoFragment fragment = new GoodsInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_goods_info;
    }

    @Override
    protected void initView() {
        // 取出数据
        String json = getArguments().getString(ARGUMENT_GOODS_INFO);
        mGoodsInfo = new Gson().fromJson(json, GoodsInfo.class);

        // ViewPager

        GoodsPictureAdapter adapter = new GoodsPictureAdapter(mGoodsInfo.getPictures()) {
            @Override
            protected void onImageClicked(Picture picture) {
                // todo 点击商品图
                ToastWrapper.show(picture.getLarge());
            }
        };
        mPicturePager.setAdapter(adapter);
        mIndicator.setViewPager(mPicturePager);

        //商品信息
        // 设置商品名称, 价格等信息
        mTvGoodsName.setText(mGoodsInfo.getName());
        mTvGoodsPrice.setText(mGoodsInfo.getShopPrice());

        String marketPrice = mGoodsInfo.getMarketPrice();
        SpannableString spannableString = new SpannableString(marketPrice);
        spannableString.setSpan(new StrikethroughSpan(),0,marketPrice.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvMarketPrice.setText(spannableString);
        mBtnFavorite.setSelected(mGoodsInfo.isCollected());

    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity responseEntity) {

    }

    @OnClick(R.id.text_goods_comments) void changeToComments() {
        // 切换到商品评价Fragment
        GoodsActivity activity = (GoodsActivity) getActivity();
        activity.selectPage(2);
    }

    @OnClick(R.id.text_goods_details) void changeToDetails() {
        // 切换到商品详情Fragment
        GoodsActivity activity = (GoodsActivity) getActivity();
        activity.selectPage(1);
    }

    @OnClick(R.id.button_favorite) void collectGoods() {
        // 收藏商品
        ToastWrapper.show(mGoodsInfo.getName());
    }
}
