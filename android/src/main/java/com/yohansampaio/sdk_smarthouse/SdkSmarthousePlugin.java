package com.yohansampaio.sdk_smarthouse;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.tuya.smart.android.user.api.IRegisterCallback;
import com.tuya.smart.android.user.bean.User;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.home.sdk.callback.ITuyaGetHomeListCallback;
import com.tuya.smart.sdk.api.INeedLoginListener;
import com.tuya.smart.sdk.api.IResultCallback;
import com.yohansampaio.sdk_smarthouse.TuyaPackage.TuyaDevices;
import com.yohansampaio.sdk_smarthouse.TuyaPackage.TuyaHome;
import com.yohansampaio.sdk_smarthouse.TuyaPackage.TuyaRegister;

import java.util.HashMap;
import java.util.List;

import io.flutter.Log;
import io.flutter.app.FlutterApplication;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.view.FlutterMain;

import static android.content.ContentValues.TAG;

/**
 * SdkSmarthousePlugin
 */
public class SdkSmarthousePlugin extends FlutterApplication implements FlutterPlugin, MethodCallHandler
        , ActivityAware {

    private MethodChannel channel;
    private TuyaRegister tuyaRegister = new TuyaRegister();
    private TuyaHome tuyaHome = new TuyaHome();
    private TuyaDevices tuyaDevices = new TuyaDevices();
    private Context context;
    final String client_id = "p3xmfsppye34uynvund7";
    final String secretKey = "fxedd9aqr4a77ds5hvurru4amn8n9934";
    Activity activity;
    String validateCode = "";
    String email = "";
    String countryCode = "";
    String password = "";
    String nameHome = "";
    String geoNomeHome = "";
    String ssid = "";
    String passwordNetwork = "";
    double lonHome = 0;
    double latHome = 0;
    List<String> roomsHome;



    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "sdk_smarthouse");
        channel.setMethodCallHandler(this);
        FlutterMain.startInitialization(flutterPluginBinding.getApplicationContext());
        context = flutterPluginBinding.getApplicationContext();
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "sdk_smarthouse");
        channel.setMethodCallHandler(new SdkSmarthousePlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull final Result result) {
        switch (call.method) {

            case "initTuya":
                final HashMap<String, String> resulInit = new HashMap<>();
                try {
                    TuyaHomeSdk.init(activity.getApplication(), client_id, secretKey);
                    TuyaHomeSdk.setDebugMode(true);
                    resulInit.put("code", "200");
                    resulInit.put("mensage", "sucess");
                } catch (Exception e) {
                    resulInit.put("code", "100");
                    resulInit.put("mensage", e.toString());
                    Log.d(TAG, e.toString());
                }

                break;
            case "destroyTuya":
                TuyaHomeSdk.onDestroy();
                break;


            case "getRegisterCode":
                email = call.argument("email");
                countryCode = call.argument("countryCode");

                HashMap<String, String> resultRegisterCode
                        = tuyaRegister.GetRegisterCode(countryCode, email);
                result.success(resultRegisterCode);
                break;


            case "createAccount":
                validateCode = call.argument("codeValidate");
                email = call.argument("email");
                countryCode = call.argument("countryCode");
                password = call.argument("password");

                HashMap<String, String> resultCreatAccount
                        = tuyaRegister.CreateAccount(countryCode, email, password, validateCode);
                result.success(resultCreatAccount);
                break;


            case "needLogin":
                TuyaHomeSdk.setOnNeedLoginListener(new INeedLoginListener() {
                    boolean isNeedLogin;

                    @Override
                    public void onNeedLogin(Context context) {

                    }
                });

                break;

            case "getHomeManager":

                HashMap<String, String> resultHomeInstance = tuyaHome.getHomeInstance();
                result.success(resultHomeInstance);
                break;

            case "getUid":
                String uid = tuyaRegister.GetUui();
                System.out.println(uid);
                result.success(uid);
                break;

            case "createHome":
                nameHome = call.argument("nameHome");
                lonHome = call.argument("lonHome");
                latHome = call.argument("latHome");
                geoNomeHome = call.argument("geoNomeHome");
                roomsHome = call.argument("roomsHome");
                HashMap<String, String> resultHomeCreate = tuyaHome.createHome(nameHome, lonHome
                        ,latHome,geoNomeHome, roomsHome);
                result.success(resultHomeCreate);
                break;

            case "searchDevices":
                ssid = call.argument("ssid");
                passwordNetwork = call.argument("passwordNetwork");

                HashMap<String, String> resultToken = tuyaDevices
                        .getTokenNetwork(context, ssid, passwordNetwork);

                String code = resultToken.get("code");
                String token = resultToken.get("result");
                System.out.println(code + " token: " + token);

                break;

            default:
                result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        activity = binding.getActivity();

    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    }

    @Override
    public void onDetachedFromActivity() {

    }
}
