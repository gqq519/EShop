package com.feicuiedu.eshop.feature.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.network.entity.CategoryBase;
import com.feicuiedu.eshop.network.entity.CategoryPrimary;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gqq on 2017/2/10.
 */

// 封装前的二级分类适配器
public class ChildrenAdapter extends BaseAdapter {

    private List<CategoryBase> mData = new ArrayList<>();

    public void addAll(List<CategoryBase> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public CategoryBase getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ChildrenViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_primary_category, viewGroup, false);
            holder = new ChildrenViewHolder(view);
            view.setTag(holder);
        }

        holder = (ChildrenViewHolder) view.getTag();
        holder.mTextCategory.setText(mData.get(i).getName());
        return view;
    }


    static class ChildrenViewHolder {
        @BindView(R.id.text_category)
        TextView mTextCategory;

        ChildrenViewHolder(View view) {
            ButterKnife.bind(this, view);
//            mTextCategory = (TextView) view;

        }
    }
}
