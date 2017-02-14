package com.feicuiedu.eshop.feature.home;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.base.BaseListAdapter;
import com.feicuiedu.eshop.network.entity.CategoryHome;
import com.feicuiedu.eshop.network.entity.SimpleGoods;

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
                        Toast.makeText(getContext(), goods.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        @Override
        protected void bind(int position) {
            mItem = getItem(position);
            tvCategory.setText(mItem.getName());
            List<SimpleGoods> goodsList = mItem.getHotGoodsList();

            for (int i = 0; i < imageViews.length; i++) {
                goodsList.get(i).getImg();
                imageViews[i].setImageResource(R.drawable.image_holder_goods);
            }
        }

        @OnClick(R.id.text_category) void onClick() {
            Toast.makeText(getContext(), mItem.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
