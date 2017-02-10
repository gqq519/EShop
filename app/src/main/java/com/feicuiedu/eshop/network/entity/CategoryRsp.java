package com.feicuiedu.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gqq on 2017/2/10.
 */

// 暂时使用的商品分类的响应体
public class CategoryRsp {

    @SerializedName("data")
    private List<CategoryPrimary> mData;

    @SerializedName("status")
    private Status mStatus;

    public Status getStatus() {
        return mStatus;
    }

    public List<CategoryPrimary> getData() {
        return mData;
    }
}
