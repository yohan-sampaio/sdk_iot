import 'dart:async';


import 'package:flutter/services.dart';

class SdkSmarthouse {


  static const MethodChannel _channel =
      const MethodChannel('sdk_smarthouse');

  

  static Future<Map<dynamic, dynamic> > getRegisterCode(String email, String countryCode) async {
    final Map<dynamic, dynamic>  result = await _channel.invokeMethod('getRegisterCode', 
    {"email" : email,
    "countryCode" : countryCode});
    return result;
  }

  static Future<Map<dynamic, dynamic> >  createAccount(String code, String email, String countryCode, String password) async {
    final Map<dynamic, dynamic> result = await _channel.invokeMethod('createAccount', {
      "codeValidate" : code,
      "email": email,
      "countryCode":countryCode,
      "password":password
      });
    return result;
  }

  static Future<Map<dynamic, dynamic>>  initTuya(String client_id, String appSecret) async {
    final Map<dynamic, dynamic> result = await _channel.invokeMethod('initTuya',
    {
      "client_id" : client_id,
      "app_Secret" : appSecret
    });

    return result;
  }


  static Future<String>  get destroyTuya async {
    final String result = await _channel.invokeMethod('destroyTuya');
    return result;
  }

  static Future<Map<dynamic,dynamic>>get homeInstance async {
    final  Map<dynamic,dynamic>result = await _channel.invokeMethod('getHomeManager');
    return result;
  }


  static Future<String>  get userUid async {
    final String result = await _channel.invokeMethod('getUid');
    return result;
  }

  static Future<String>  createHome(String nameHome, String geoNameHome
      , double lonHome, double latHome, List<String> roomsHome) async {
    final String result = await _channel.invokeMethod('createHome',
    {
      "nameHome" : nameHome,
      "latHome" : latHome,
      "lonHome" : lonHome,
      "roomsHome" : roomsHome
    });
    return result;
  }
}
