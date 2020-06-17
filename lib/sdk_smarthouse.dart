import 'dart:async';


import 'package:flutter/services.dart';

class SdkSmarthouse {
  static const MethodChannel _channel =
      const MethodChannel('sdk_smarthouse');

  static Future<String> get platformVersion async {

    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
