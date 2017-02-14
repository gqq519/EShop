package com.feicuiedu.eshop.base.wrapper;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by gqq on 2017/2/14.
 */

// Toast的封装类
public class ToastWrapper {

    private static Context mContext;

    private static Toast mToast;

    // 直接类名调用方法，上下文的传递，提供一个初始化的方法，在使用之前使用，可以放在Application里面
    public static void init(Context context){
        mContext = context;
        mToast = Toast.makeText(context,null,Toast.LENGTH_SHORT);
        mToast.setDuration(Toast.LENGTH_SHORT);
    }

    public static void show(int resId,Object... args){
        String text = mContext.getString(resId, args);
        mToast.setText(text);
        mToast.show();
    }

    public static void show(CharSequence charSequence,Object... args){
        String text = String.format(charSequence.toString(), args);
        mToast.setText(text);
        mToast.show();
    }



}
