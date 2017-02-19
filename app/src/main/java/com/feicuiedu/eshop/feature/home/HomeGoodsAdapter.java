package com.feicuiedu.eshop.feature.home;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.base.BaseListAdapter;
import com.feicuiedu.eshop.base.wrapper.ToastWrapper;
import com.feicuiedu.eshop.feature.goods.GoodsActivity;
import com.feicuiedu.eshop.network.entity.CategoryHome;
import com.feicuiedu.eshop.network.entity.Picture;
import com.feicuiedu.eshop.network.entity.SimpleGoods;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class HomeGoodsAdapter extends BaseListAdapter<CategoryHome, HomeGoodsAdapter.ViewHolder> {


    @Override
    protected int getItemViewLayout() {
        return R.layout.item_home_goods;
    }

    @Override
    protected ViewHolder getItemViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    class ViewHolder extends BaseListAdapter.ViewHolder {

        @BindView(R.id.text_category)
        TextView tvCategory;

        @BindViews({
                R.id.image_goods_01,
                R.id.image_goods_02,
                R.id.image_goods_03,
                R.id.image_goods_04
        })
        ImageView[] imageViews;

        private CategoryHome mItem;

        ViewHolder(View itemView) {
            super(itemView);

            for (int i = 0; i < imageViews.length; i++) {
                final int index = i;
                imageViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleGoods goods = mItem.getHotGoodsList().get(index);
                        ToastWrapper.show(goods.getName());
                    }
                });
            }
        }

        @Override
        protected void bind(final int position) {
            mItem = getItem(position);
            tvCategory.setText(mItem.getName());
            final List<SimpleGoods> goodsList = mItem.getHotGoodsList();

            for (int i = 0; i < imageViews.length; i++) {
                Picture picture = goodsList.get(i).getImg();
                Picasso.with(getContext()).load(picture.getLarge()).into(imageViews[i]);
//                imageViews[i].setImageResource(R.drawable.image_holder_goods);
                imageViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ToastWrapper.show("到商品详情页");
                        Intent intent = GoodsActivity.getStartIntent(getContext(), goodsList.get(position).getId());
                        getContext().startActivity(intent);

                    }
                });
            }
        }

        @OnClick(R.id.text_category) void onClick() {
            Toast.makeText(getContext(), mItem.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
