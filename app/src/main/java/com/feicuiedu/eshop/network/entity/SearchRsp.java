package com.feicuiedu.eshop.network.entity;


import com.feicuiedu.eshop.network.core.ResponseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchRsp extends ResponseEntity{

    @SerializedName("data") private List<SimpleGoods> mData;

    @SerializedName("paginated") private Paginated mPaginated;

    public List<SimpleGoods> getData() {
        return mData;
    }

    public Paginated getPaginated() {
        return mPaginated;
    }

}
