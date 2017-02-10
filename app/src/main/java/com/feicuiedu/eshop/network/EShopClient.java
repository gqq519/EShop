package com.feicuiedu.eshop.network;

import com.feicuiedu.eshop.network.entity.CategoryRsp;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by gqq on 2017/2/10.
 */

public class EShopClient {

    public static final String BASE_URL = "http://106.14.32.204/eshop/emobile/?url=";

    private static EShopClient mEShopClient;
    private final OkHttpClient mOkHttpClient;

    public static synchronized EShopClient getInstance() {
        if (mEShopClient == null) {
            mEShopClient = new EShopClient();
        }
        return mEShopClient;
    }

    private EShopClient() {

        // 日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // OkHttp的实例
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    // 获取商品分类的数据
    public CategoryRsp getCategory() {

        CategoryRsp categoryRsp = new CategoryRsp();

        // 构建请求和拿到响应

        Request request = new Request.Builder()
                .get()
                .url(BASE_URL + "/category")
                .build();

        try {
            // 拿到响应解析
            Response response = mOkHttpClient.newCall(request).execute();
            if (response != null) {
                ResponseBody body = response.body();
                categoryRsp =  new Gson().fromJson(body.string(), CategoryRsp.class);
            }

        } catch (IOException e) {
            throw new RuntimeException();
        }

        return categoryRsp;
    }
}
