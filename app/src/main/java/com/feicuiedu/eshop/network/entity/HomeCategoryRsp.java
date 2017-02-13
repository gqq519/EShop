package com.feicuiedu.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gqq on 2017/2/13.
 */

public class HomeCategoryRsp {

    @SerializedName("data")
    private List<CategoryHome> mData;

    @SerializedName("status")
    private Status mStatus;

    public List<CategoryHome> getData() {
        return mData;
    }

    public Status getStatus() {
        return mStatus;
    }
}
