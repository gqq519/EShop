package com.feicuiedu.eshop.network.entity;

import com.feicuiedu.eshop.network.core.ResponseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gqq on 2017/2/13.
 */

public class HomeBannerRsp extends ResponseEntity{

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data {

        @SerializedName("player")
        private List<Banner> mBanners;

        @SerializedName("promote_goods")
        private List<SimpleGoods> mGoodsList;

        public List<Banner> getBanners() {
            return mBanners;
        }

        public List<SimpleGoods> getGoodsList() {
            return mGoodsList;
        }
    }

}
