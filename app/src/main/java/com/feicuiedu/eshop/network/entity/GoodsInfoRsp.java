package com.feicuiedu.eshop.network.entity;

import com.feicuiedu.eshop.network.core.ResponseEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gqq on 17/2/19.
 */

// 商品详情的响应体
public class GoodsInfoRsp extends ResponseEntity{

    @SerializedName("data")
    private GoodsInfo mData;

    public GoodsInfo getData() {
        return mData;
    }

}
