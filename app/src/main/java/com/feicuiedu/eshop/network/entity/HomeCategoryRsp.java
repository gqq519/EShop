package com.feicuiedu.eshop.network.entity;

import com.feicuiedu.eshop.network.core.ResponseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gqq on 2017/2/13.
 */

public class HomeCategoryRsp extends ResponseEntity{

    @SerializedName("data")
    private List<CategoryHome> mData;

    public List<CategoryHome> getData() {
        return mData;
    }

}
