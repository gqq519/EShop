package com.feicuiedu.eshop.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by gqq on 2017/2/13.
 */

/**
 * 适配器的基类
 * 1. 适配器中需要有数据
 * 2. 需要有ViewHolder
 * 但是不知道数据类型是什么、视图是什么，所以使用泛型
 */

public abstract class BaseListAdapter<T, V extends BaseListAdapter.ViewHolder> extends BaseAdapter {

    // 数据
    private List<T> mData = new ArrayList<>();

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getItemViewLayout(), parent, false);
            viewHolder = getItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.bind(position);

        return convertView;
    }

    // 添加数据方法：加载等
    public void addAll(List<T> data){
        if (data!=null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    // 重置数据：数据更新等
    public void reset(List<T> data){
        mData.clear();
        if (data!=null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    @LayoutRes
    protected abstract int getItemViewLayout();

    protected abstract V getItemViewHolder(View itemView);

    public abstract class ViewHolder {
        // 视图
        View itemView;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this,itemView);
            this.itemView = itemView;
        }

        public abstract void bind(int position);

        // 上下文，引出：如果某些需要上下文的事件在适配里面写(itemView的某一控件的点击跳转事件等)，所以在对外提供一个上下文
        public Context getContext(){
            return itemView.getContext();
        }
    }
}
