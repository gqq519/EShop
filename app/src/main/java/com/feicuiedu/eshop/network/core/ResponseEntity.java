package com.feicuiedu.eshop.network.core;

import com.feicuiedu.eshop.network.entity.Status;
import com.google.gson.annotations.SerializedName;

/**
 * 响应体的基类.
 * 服务器接口的抽象出来的基类，真正响应的数据都基于此(继承自此)，为了防止直接实现，所以定义为抽象的，只能通过其子类来实例化。
 */
public abstract class ResponseEntity {

    @SerializedName("status") private Status mStatus;

    public Status getStatus() {
        return mStatus;
    }
}
