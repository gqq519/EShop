package com.feicuiedu.eshop.feature.goods.info;

import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.network.entity.Picture;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gqq on 17/2/19.
 */

public abstract class GoodsPictureAdapter extends PagerAdapter {

    List<Picture> mPictureList = new ArrayList<>();

    public GoodsPictureAdapter(List<Picture> pictureList) {
        mPictureList.addAll(pictureList);
    }

    @Override
    public int getCount() {
        return mPictureList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = (ImageView) LayoutInflater.from(container.getContext()).inflate(R.layout.item_goods_picture,container,false);
        container.addView(imageView);

        // 设置数据
        final Picture picture = mPictureList.get(position);
        Picasso.with(container.getContext()).load(picture.getLarge()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击事件
                onImageClicked(picture);

            }
        });
        return imageView;
    }

    protected abstract void onImageClicked(Picture picture);

}
