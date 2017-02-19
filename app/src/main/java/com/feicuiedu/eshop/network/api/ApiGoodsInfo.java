package com.feicuiedu.eshop.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.feicuiedu.eshop.network.core.ApiInterface;
import com.feicuiedu.eshop.network.core.ApiPath;
import com.feicuiedu.eshop.network.core.RequestParam;
import com.feicuiedu.eshop.network.core.ResponseEntity;
import com.feicuiedu.eshop.network.entity.GoodsInfoReq;
import com.feicuiedu.eshop.network.entity.GoodsInfoRsp;

/**
 * Created by gqq on 17/2/19.
 */

public class ApiGoodsInfo implements ApiInterface {

    private GoodsInfoReq mGoodsInfoReq;

    public ApiGoodsInfo(int goodsId) {
        mGoodsInfoReq = new GoodsInfoReq();
        mGoodsInfoReq.setGoodsId(goodsId);
    }

    @NonNull
    @Override
    public String getPath() {
        return ApiPath.GOODSINFO;
    }

    @Nullable
    @Override
    public RequestParam getRequestParam() {
        return mGoodsInfoReq;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntityType() {
        return GoodsInfoRsp.class;
    }
}
