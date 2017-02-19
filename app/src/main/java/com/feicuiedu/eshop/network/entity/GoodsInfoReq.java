package com.feicuiedu.eshop.network.entity;

import com.feicuiedu.eshop.network.core.RequestParam;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gqq on 17/2/19.
 */

// 商品详情请求体
public class GoodsInfoReq extends RequestParam{

    @SerializedName("goods_id")
    private int mGoodsId;

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        mGoodsId = goodsId;
    }
}
