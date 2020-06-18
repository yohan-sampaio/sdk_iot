import 'dart:async';


import 'package:flutter/services.dart';

class SdkSmarthouse {
  static const MethodChannel _channel =
      const MethodChannel('sdk_smarthouse');

  static Future<String> createAccount(String email, String countryCode) async {
    final String result = await _channel.invokeMethod('createAccount', 
    {"email" : email,
    "countryCode" : countryCode});
    return result;
  }

  static Future<String>  validateCode(String code, String email, String countryCode, String password) async {
    final String result = await _channel.invokeMethod('validateCode', {
      "codeValidate" : code,
      "email": email,
      "countryCode":countryCode,
      "password":password
      });
    return result;
  }

  static Future<String>  get initTuya async {
    final String result = await _channel.invokeMethod('initTuya');
    return result;
  }


  static Future<String>  get destroyTuya async {
    final String result = await _channel.invokeMethod('destroyTuya');
    return result;
  }

  static Future<List>  get homeInstance async {
    final List<dynamic> result = await _channel.invokeMethod('getHomeManager');
    return result;
  }
}
