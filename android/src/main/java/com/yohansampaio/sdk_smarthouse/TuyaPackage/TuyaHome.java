package com.yohansampaio.sdk_smarthouse.TuyaPackage;

import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.home.sdk.bean.RoomBean;
import com.tuya.smart.home.sdk.callback.ITuyaGetHomeListCallback;
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback;

import java.util.HashMap;
import java.util.List;

public class TuyaHome {

    public HashMap<String, String> getHomeInstance(){
        final HashMap<String, String> result = new HashMap<>();
        TuyaHomeSdk.getHomeManagerInstance().queryHomeList(new ITuyaGetHomeListCallback() {
            @Override
            public void onSuccess(List<HomeBean> homeBeans) {
                System.out.println("Home" + homeBeans.toString());
                result.put("codeResult", "200");
                result.put("result", homeBeans.toString());
            }

            @Override
            public void onError(String errorCode, String error) {
                System.out.println(error);
                result.put("codeResult", errorCode);
                result.put("result", error);
            }
        });
        return result;
    }

    public HashMap<String, String> createHome(String name, double lon, double lat, String geoName
            ,List<String> rooms){
        final HashMap<String, String> result = new HashMap<>();
        TuyaHomeSdk.getHomeManagerInstance().createHome(name, lon, lat, geoName, rooms, new ITuyaHomeResultCallback() {
            @Override
            public void onSuccess(HomeBean bean) {
                System.out.println("Home" + bean.toString());
                result.put("codeResult", "200");
                result.put("result", "Home inserted with sucessful");
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                System.out.println(errorMsg);
                result.put("codeResult", errorCode);
                result.put("result", errorMsg);
            }
        });
        return result;
    }
}
