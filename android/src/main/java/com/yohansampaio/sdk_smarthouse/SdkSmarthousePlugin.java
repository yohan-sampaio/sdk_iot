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
import com.yohansampaio.sdk_smarthouse.TuyaPackage.TuyaInit;

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
    private TuyaInit tuyaInit = new TuyaInit();

    Activity activity;
    private Context context;
    final String client_id = "p3xmfsppye34uynvund7";
    final String secretKey = "fxedd9aqr4a77ds5hvurru4amn8n9934";


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
                try {
                    TuyaHomeSdk.init(activity.getApplication(), client_id, secretKey);
                    TuyaHomeSdk.setDebugMode(true);
                    result.success("ok");
                } catch (Exception e) {
                    result.error("104", e.getMessage(), "");
                }

                break;
            case "destroyTuya":
                TuyaHomeSdk.onDestroy();
              break;


            case "createAccount":
                TuyaHomeSdk.getUserInstance().getRegisterEmailValidateCode("55",
                        "yohan.develop@gmail.com", new IResultCallback() {
                            @Override
                            public void onError(String code, String error) {
                                System.out.println(error);
                            }

                            @Override
                            public void onSuccess() {
                                System.out.println("deu boa");
                            }
                        });
                break;


            case "validateCode":
                final String validateCode = call.argument("codeValidate");
                final String email = call.argument("email");
                final String countryCode = call.argument("countryCode");
                final String password = call.argument("password");

                try {
                  TuyaHomeSdk.getUserInstance().registerAccountWithEmail(countryCode,
                          email, password, validateCode, new IRegisterCallback() {
                            @Override
                            public void onSuccess(User user) {
                              result.success("Email validado.");
                            }

                            @Override
                            public void onError(String code, String error) {
                              result.error(code, error, "");
                            }
                          });
                }catch (Exception e){
                  result.error("ErrValidate", e.toString(), "");
                  Log.d(TAG, e.toString());
                }
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
            TuyaHomeSdk.getHomeManagerInstance().queryHomeList(new ITuyaGetHomeListCallback() {
              @Override
              public void onSuccess(List<HomeBean> homeBeans) {
                System.out.println("Home" + homeBeans.toString());
              }

              @Override
              public void onError(String errorCode, String error) {
                System.out.println(error);
              }
            });

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
