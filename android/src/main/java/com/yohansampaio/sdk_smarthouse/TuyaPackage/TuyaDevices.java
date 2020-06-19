package com.yohansampaio.sdk_smarthouse.TuyaPackage;

import android.content.Context;

import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.builder.ActivatorBuilder;
import com.tuya.smart.sdk.api.ITuyaActivator;
import com.tuya.smart.sdk.api.ITuyaActivatorCreateToken;
import com.tuya.smart.sdk.api.ITuyaActivatorGetToken;
import com.tuya.smart.sdk.api.ITuyaSmartActivatorListener;
import com.tuya.smart.sdk.bean.DeviceBean;
import com.tuya.smart.sdk.enums.ActivatorModelEnum;

import java.util.HashMap;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TuyaDevices {

    public HashMap<String, String> getTokenNetwork(final Context context, final String ssid
            , final String password){
        final HashMap<String, String> result = new HashMap<>();
        TuyaHomeSdk.getActivatorInstance().getActivatorToken(18765421, new ITuyaActivatorGetToken() {
            @Override
            public void onSuccess(String token) {
                result.put("code", "200");
                result.put("result",token);
                System.out.println(token);
                connectToNetwork(context, ssid, token, password);
            }

            @Override
            public void onFailure(String errorCode, String errorMsg) {
                result.put("code", errorCode);
                result.put("result",errorMsg);
                System.out.println(errorMsg);
            }
        });
        return result;
    }

    public HashMap<String, String> connectToNetwork(Context context, String ssid, String token
            , String password){
        final HashMap<String, String> result = new HashMap<>();
        final ActivatorBuilder activatorBuilder = new ActivatorBuilder()
                .setContext(context)
                .setPassword(password)
                .setSsid(ssid)
                .setToken(token)
                .setTimeOut(100)
                .setActivatorModel(ActivatorModelEnum.TY_EZ).setListener(new ITuyaSmartActivatorListener() {
                    @Override
                    public void onError(String errorCode, String errorMsg) {
                        result.put("code", errorCode);
                        result.put("result", errorMsg);
                        System.out.println(errorMsg);
                    }

                    @Override
                    public void onActiveSuccess(DeviceBean devResp) {

                        result.put("code", "200");
                        result.put("result", "sucess");
                        System.out.println("Sucesso aqui");
                    }

                    @Override
                    public void onStep(String step, Object data) {
                        result.put("code", "300");
                        result.put("result", step);
                        System.out.println(step);
                    }
                });

        ITuyaActivator mTuyaActivator = TuyaHomeSdk.getActivatorInstance()
                .newMultiActivator(activatorBuilder);
        mTuyaActivator.start();
        try {

            Thread.sleep(10000);

        } catch (InterruptedException e) {
            e.printStackTrace();
            mTuyaActivator.stop();
            mTuyaActivator.onDestroy();
        }
        return result;
    }


}
