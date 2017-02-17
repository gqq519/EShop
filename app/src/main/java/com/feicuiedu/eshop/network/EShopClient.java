package com.feicuiedu.eshop.network;

import android.os.Handler;

import com.feicuiedu.eshop.network.core.ApiInterface;
import com.feicuiedu.eshop.network.core.RequestParam;
import com.feicuiedu.eshop.network.core.ResponseEntity;
import com.feicuiedu.eshop.network.core.UiCallback;
import com.feicuiedu.eshop.network.entity.CategoryRsp;
import com.feicuiedu.eshop.network.entity.HomeBannerRsp;
import com.feicuiedu.eshop.network.entity.HomeCategoryRsp;
import com.feicuiedu.eshop.network.entity.SearchReq;
import com.feicuiedu.eshop.network.entity.SearchRsp;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    private Gson mGson;

    public static synchronized EShopClient getInstance() {
        if (mEShopClient == null) {
            mEShopClient = new EShopClient();
        }
        return mEShopClient;
    }

    private EShopClient() {

        mGson = new Gson();

        // 日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // OkHttp的实例
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }


    // 将响应转换为相应的实体类型
    public  <T extends ResponseEntity>T getResponseEntity(Response response, Class<T> clazz) throws IOException {

        if (!response.isSuccessful()){
            throw new IOException("Response code is"+response.code());
        }
        return mGson.fromJson(response.body().string(),clazz);
    }

    /**
     * 之前构建请求的方法重复性代码太多，所以进行封装
     * 每一个请求构建过程都一样，不同的是请求地址、请求参数、响应类型
     * 请求分为两种：同步和异步
     */

    // 同步方法
    public <T extends ResponseEntity>T execute(ApiInterface apiInterface) throws IOException {
        // 将构建的过程提取成一个方法

        Response response = newApiCall(apiInterface,null).execute();
        Class<T> entityType = (Class<T>) apiInterface.getResponseEntityType();
        return getResponseEntity(response,entityType);
    }

    // 异步方法
    public Call enqueue(ApiInterface apiInterface,
                        UiCallback uiCallback,
                        String tag){
        // 将构建的过程提取成一个方法
        Call call = newApiCall(apiInterface,tag);
        call.enqueue(uiCallback);
        uiCallback.setResponseType(apiInterface.getResponseEntityType());
        return call;

    }

    private Call newApiCall(ApiInterface apiInterface,String tag) {
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + apiInterface.getPath());

        if (apiInterface.getRequestParam() != null) {
            String param = mGson.toJson(apiInterface.getRequestParam());
            FormBody formBody = new FormBody.Builder()
                    .add("json", param)
                    .build();
            builder.post(formBody);
        }
        builder.tag(tag);
        Request request = builder.build();
        return mOkHttpClient.newCall(request);
    }

    public void cancleByTag(String tag){
        // 取消的时候，多个call在队列中顺序执行，首先关闭队伍中的，再关闭正在执行的
        for (Call call:mOkHttpClient.dispatcher().queuedCalls()){
            if (call.request().tag().equals(tag))
                call.cancel();
        }

        for (Call call:mOkHttpClient.dispatcher().runningCalls()){
            if (call.request().tag().equals(tag))
                call.cancel();
        }
    }
}
