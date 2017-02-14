package com.feicuiedu.eshop.feature;

import android.app.Application;

import com.feicuiedu.eshop.base.wrapper.ToastWrapper;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by gqq on 2017/2/7.
 */

public class EshopApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)){

            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.

            // 此进程是LeakCanary用于内存堆分析的.
            // 不应该在此进程中做任何app初始化工作.
            return;
        }
        LeakCanary.install(this);

        // 项目正常初始化工作
        ToastWrapper.init(this);
    }
}
