package com.feicuiedu.eshop.network.api;

import com.feicuiedu.eshop.network.EShopClient;
import com.feicuiedu.eshop.network.core.ResponseEntity;
import com.feicuiedu.eshop.network.entity.GoodsInfoRsp;

import org.junit.Test;

import okhttp3.Call;

import static org.junit.Assert.*;

/**
 * Created by gqq on 17/2/19.
 */
public class ApiGoodsInfoTest {

    @Test
    public void getGoodsIndfo() throws Exception{
        GoodsInfoRsp goodsInfoRsp = EShopClient.getInstance().execute(new ApiGoodsInfo(78));
        assertTrue(goodsInfoRsp.getStatus().isSucceed());
    }

}