package com.feicuiedu.eshop.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.feicuiedu.eshop.network.core.ApiInterface;
import com.feicuiedu.eshop.network.core.ApiPath;
import com.feicuiedu.eshop.network.core.RequestParam;
import com.feicuiedu.eshop.network.core.ResponseEntity;
import com.feicuiedu.eshop.network.entity.CategoryRsp;
import com.feicuiedu.eshop.network.entity.HomeBannerRsp;
import com.feicuiedu.eshop.network.entity.HomeCategoryRsp;
import com.feicuiedu.eshop.network.entity.SearchReq;
import com.feicuiedu.eshop.network.entity.SearchRsp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gqq on 2017/2/10.
 */
public class EShopClientTest {

    private EShopClient mClient;

    @Before
    public void setUp() throws Exception {
        mClient = EShopClient.getInstance();
    }

    // 分类：商品分类
    @Test
    public void excute() throws Exception {
        ApiInterface apiInterface = new ApiInterface() {
            @NonNull
            @Override
            public String getPath() {
                return ApiPath.CATEGORY;
            }

            @Nullable
            @Override
            public RequestParam getRequestParam() {
                return null;
            }

            @NonNull
            @Override
            public Class<? extends ResponseEntity> getResponseEntityType() {
                return CategoryRsp.class;
            }
        };
        CategoryRsp categoryRsp = mClient.execute(apiInterface);
        assertTrue(categoryRsp.getStatus().isSucceed());
    }
}