package com.feicuiedu.eshop.base.widgets.banner;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feicuiedu.eshop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gqq on 2017/2/14.
 */

public abstract class BannerAdapter<T> extends PagerAdapter {

    private List<T> mDataSet = new ArrayList<>();

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        ViewHolder holder = (ViewHolder) object;
        return view==holder.itemView;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View itemView = inflater.inflate(R.layout.item_banner, container, false);
        container.addView(itemView);
        ViewHolder holder = new ViewHolder(itemView);

        // 绑定数据
        bind(holder,mDataSet.get(position));

        return holder;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewHolder holder = (ViewHolder) object;
        container.removeView(holder.itemView);
    }

    // 对外提供一个更新数据的方法
    public void reset(List<T> dataSet){
        mDataSet.clear();
        if (dataSet!=null){
            mDataSet.addAll(dataSet);
        }
        notifyDataSetChanged();
    }

    protected abstract void bind(ViewHolder holder,T data);

    public static class ViewHolder{

        @BindView(R.id.image_banner_item)
        public ImageView mImageBannerItem;
        public View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this,itemView);

        }
    }
}
