package com.feicuiedu.eshop.network;

import com.feicuiedu.eshop.network.entity.CategoryRsp;

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

    @Test
    public void getCategory() throws Exception {
        CategoryRsp category = mClient.getCategory();

        // 判断状态是不是成功
        assertTrue(category.getStatus().isSucceed());
    }
}