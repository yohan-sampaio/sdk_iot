package com.yohansampaio.sdk_smarthouse.TuyaPackage;
import android.app.Application;

import com.tuya.smart.android.user.api.IRegisterCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.sdk.api.IResultCallback;
import com.tuya.spongycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;

import java.util.HashMap;

import io.flutter.Log;

import static android.content.ContentValues.TAG;


public class TuyaRegister extends Application {

    public HashMap<String, String> GetRegisterCode(String countryCode, final String email){
        final HashMap<String,String> result = new HashMap<>();
        TuyaHomeSdk.getUserInstance().getRegisterEmailValidateCode(countryCode,
                email, new IResultCallback() {
                    @Override
                    public void onError(String code, String error) {
                        result.put("code", code);
                        result.put("mensage", error);
                    }

                    @Override
                    public void onSuccess() {
                        result.put("code", "200");
                        result.put("mensage", "Sucess. Code send to " + email);
                    }
                });
        return result;
    }

    public HashMap<String, String> CreateAccount(String countryCode, String email, String password
            , String validateCode){
        final HashMap<String,String> result = new HashMap<>();

        try {
            TuyaHomeSdk.getUserInstance().registerAccountWithEmail(countryCode,
                    email, password, validateCode, new IRegisterCallback() {
                        @Override
                        public void onSuccess(User user) {
                            result.put("code", "200");
                            result.put("mensage", "sucess");
                        }

                        @Override
                        public void onError(String code, String error) {
                            result.put("code", code);
                            result.put("mensage", error);
                        }
                    });
        }catch (Exception e){
            result.put("code", "100");
            result.put("mensage", e.toString());
            Log.d(TAG, e.toString());
        }
        return result;
    }

    public String GetUui(){
        String result;
        try{
           result = TuyaHomeSdk.getUserInstance().getUser().getUid();
        }catch (Exception e){
            Log.w(TAG, e.toString());
            result = "";
        }
       return result;
    }
}
