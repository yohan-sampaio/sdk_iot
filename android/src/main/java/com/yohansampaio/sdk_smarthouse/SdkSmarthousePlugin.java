package com.yohansampaio.sdk_smarthouse;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.sdk.api.IResultCallback;

import io.flutter.app.FlutterActivity;
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

/** SdkSmarthousePlugin */
public class SdkSmarthousePlugin extends FlutterApplication implements FlutterPlugin, MethodCallHandler
        , ActivityAware {

  private MethodChannel channel;
  private TuyaInit tuyaInit = new TuyaInit();

  Activity activity;
  private Context context;
  final String client_id = "5shjnpmfn7a8epxswtsu";
  final String secretKey = "rdvgmhcq8uh4w9aqepy4m7nhuh399w39";


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
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {

      Application application = activity.getApplication();


      TuyaHomeSdk.init(activity.getApplication());

      /*
      TuyaHomeSdk.getUserInstance().getRegisterEmailValidateCode("86",
              "yohan.develop@gmail.com", new IResultCallback() {
                @Override
                public void onError(String code, String error) {
                  System.out.println(error);
                }

                @Override
                public void onSuccess() {
                  System.out.println("foi caraio");
                }
              });
     */


    } else {
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
    System.out.println(activity.getApplication().getApplicationInfo());
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    this.activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivity() {
    TuyaHomeSdk.onDestroy();
  }
}
