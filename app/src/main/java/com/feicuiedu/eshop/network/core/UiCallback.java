package com.feicuiedu.eshop.network.core;

import android.os.Handler;
import android.os.Looper;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.base.utils.LogUtils;
import com.feicuiedu.eshop.base.wrapper.ToastWrapper;
import com.feicuiedu.eshop.network.EShopClient;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * UiCallback:
 * 1. 处理后台线程与主线程的交互：Handler处理
 * 2. 统一处理各种错误情况
 * 3. 将所有请求得到的响应转换为之前定义的响应体基类
 *
 * 这里直接处理错误情况，所以可以直接对外提供一个请求成功的方法，方便处理请求的数据
 */

public abstract class UiCallback implements Callback{

    // 转换的类型
    private Class<?extends ResponseEntity> mResponseType;

    // 创建主线程的Handler
    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 当网络请求由于以下原因失败时被调用: 请求被取消, 网络连接问题或者请求超时.
     * 由于网络可能在数据交换的中途发生故障, 有可能在失败前, 服务器处理了请求.
     */
    @Override
    public void onFailure(final Call call, final IOException e) {
        // 后台线程
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // 运行在主线程
                onFailureInUi(e);
            }
        });
    }

    /**
     * 当HTTP响应从服务器成功返回时被调用. 可以通过{@link Response#body}读取响应体.
     * 在响应体变为{@linkplain ResponseBody closed}状态之前, 响应会一直处于可用状态.
     * <p/>
     * 注意: 此回调在后台线程执行, 响应的接收者可以将数据发送到另一个线程来处理.
     * <p/>
     * 注意: 传输层的成功(接受到了HTTP响应码, 响应头和响应体)不代表应用层的成功:
     * {@code response}依然可能是一个失败的响应码, 例如404或500.
     */
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        // 后台线程

        // 为了方便后期调用处理，所以直接将响应数据的类型传递：根据要装换的类型
        final ResponseEntity responseEntity = EShopClient.getInstance().getResponseEntity(response,mResponseType);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onResponseInUi(responseEntity);
            }
        });
    }

    public void setResponseType(Class<? extends ResponseEntity> responseType){
        mResponseType = responseType;
    }

    // 统一处理错误
    private void onFailureInUi(IOException e) {
        LogUtils.error("onFailureInUi", e);
        ToastWrapper.show(R.string.error_network);
        onBusinessResponse(false,null);
    }

    // 处理响应
    public final void onResponseInUi(ResponseEntity responseEntity) {

        if (responseEntity == null || responseEntity.getStatus() == null) {
            throw new RuntimeException("Fatal Api Error!");
        }

        if (responseEntity.getStatus().isSucceed()) {
            onBusinessResponse(true, responseEntity);
        } else {
            ToastWrapper.show(responseEntity.getStatus().getErrorDesc());
            onBusinessResponse(false, null);
        }
    }

    public abstract void onBusinessResponse(boolean success, ResponseEntity responseEntity);
}
