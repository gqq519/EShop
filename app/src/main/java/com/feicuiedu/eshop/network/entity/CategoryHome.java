package com.feicuiedu.eshop.network.entity;


import com.google.gson.annotations.SerializedName;

import java.util.List;

// 通用Json对象：商品分类
public class CategoryHome extends CategoryBase {

    @SerializedName("goods")
    private List<SimpleGoods> mHotGoodsList; // 首页分类的推荐商品.

    public List<SimpleGoods> getHotGoodsList() {
        return mHotGoodsList;
    }
}
