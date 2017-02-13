package com.feicuiedu.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gqq on 2017/2/13.
 */

public class HomeBannerRsp {

    @SerializedName("status")
    private Status mStatus;

    @SerializedName("data")
    private Data mData;

    public Status getStatus() {
        return mStatus;
    }

    public Data getData() {
        return mData;
    }

    class Data {

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
