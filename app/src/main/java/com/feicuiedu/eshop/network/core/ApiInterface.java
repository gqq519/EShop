package com.feicuiedu.eshop.network.core;

/**
 * Created by gqq on 2017/2/17.
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Api的抽象接口，每一个子类代表一个服务器接口
 */
public interface ApiInterface {

    // 接口地址
    @NonNull String getPath();

    // 请求参数
    @Nullable RequestParam getRequestParam();

    // 响应体类型
    @NonNull Class<? extends ResponseEntity> getResponseEntityType();

}
