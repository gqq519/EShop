package com.feicuiedu.eshop.network;

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
    public void getCategory() throws Exception {
        CategoryRsp category = mClient.execute("/category",null,CategoryRsp.class);
        // 判断状态是不是成功
        assertTrue(category.getStatus().isSucceed());
    }

    // 首页：banner
    @Test
    public void getBanner() throws Exception{
        HomeBannerRsp homeBannerRsp = mClient.execute("/home/data",null,HomeBannerRsp.class);
        assertTrue(homeBannerRsp.getStatus().isSucceed());
    }

    // 首页：分类及数据
    @Test
    public void getHomeCategory() throws Exception{
        HomeCategoryRsp homeCategoryRsp = mClient.execute("/home/category", null, HomeCategoryRsp.class);
        assertTrue(homeCategoryRsp.getStatus().isSucceed());
    }

    @Test
    public void getSearchGoods() throws Exception{
        SearchReq searchReq = new SearchReq();
        SearchRsp searchGoods = mClient.execute("/search", searchReq, SearchRsp.class);
        assertTrue(searchGoods.getStatus().isSucceed());
    }
}