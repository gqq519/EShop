package com.feicuiedu.eshop.feature.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.network.entity.CategoryPrimary;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gqq on 2017/2/10.
 */

// 封装前的一级分类适配器
public class CategoryAdapter extends BaseAdapter {

    private List<CategoryPrimary> mData = new ArrayList<>();

    public void addAll(List<CategoryPrimary> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CategoryViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_primary_category, viewGroup, false);
            holder = new CategoryViewHolder(view);
            view.setTag(holder);
        }

        holder = (CategoryViewHolder) view.getTag();
        holder.mTextCategory.setText(mData.get(i).getName());
        return view;
    }

    static class CategoryViewHolder {
        @BindView(R.id.text_category)
        TextView mTextCategory;

        CategoryViewHolder(View view) {
            ButterKnife.bind(this, view);
//            mTextCategory = (TextView) view;
        }
    }
}
