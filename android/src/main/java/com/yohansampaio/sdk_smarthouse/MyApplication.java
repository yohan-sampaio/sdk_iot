package com.yohansampaio.sdk_smarthouse;

import android.app.Application;

import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.sdk.api.IResultCallback;

import io.flutter.app.FlutterApplication;

public class MyApplication extends FlutterApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("MyApplication");
        TuyaHomeSdk.init(this);
        TuyaHomeSdk.getUserInstance().getRegisterEmailValidateCode("55",
                "yohan.develop@gmail.com", new IResultCallback() {
            @Override
            public void onError(String code, String error) {
                System.out.println(error);
            }

            @Override
            public void onSuccess() {
                System.out.println("Deu boa");
            }
        });
    }
}
