import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:sdk_smarthouse/sdk_smarthouse.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _validateCodeController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  String _platformVersion = 'Unknown';


  @override
  void initState() {
    super.initState();
    initPlatformState();

  }


  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      //platformVersion = await SdkSmarthouse.platformVersion;
      await SdkSmarthouse.initTuya;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(children: [
          Padding(
            padding: EdgeInsets.all(10),
            child: TextField(
              controller: _emailController,
              decoration: InputDecoration(
                labelText: "Email",
              ),
            ),
          ),
          SizedBox(
            height: 60,
            width: 280,
            child: Container(
            height: 40,
            width: 220,
            padding: EdgeInsets.all(10),
            child: RaisedButton(
              child: Text("Cadastrar"),
                onPressed: () async {
                  if(_emailController.text.isNotEmpty){
                   await SdkSmarthouse.createAccount(_emailController.text, "55");
                  }
                },
            ),),
          ),
          Padding(
            padding: EdgeInsets.all(10),
            child: TextField(
              controller: _validateCodeController,
              decoration: InputDecoration(
                labelText: "Codigo de Validacao",
              ),
            ),
          ),
          Padding(
            padding: EdgeInsets.all(10),
            child: TextField(
              controller: _passwordController,
              decoration: InputDecoration(
                labelText: "Senha",
              ),
              obscureText: true,
            ),
          ),
            SizedBox(
              height: 60,
              width: 280,
              child: Container(
                padding: EdgeInsets.all(10),
                child: RaisedButton(
                  color: Colors.blue,
                  child: Text("Cadastrar"),
                    onPressed: () async {
                      if(_emailController.text.isNotEmpty && _validateCodeController.text.isNotEmpty && _passwordController.text.isNotEmpty){
                      await SdkSmarthouse.validateCode(_validateCodeController.text, 
                      _emailController.text, 
                      "55", 
                      _passwordController.text);
                      }
                    },
                ),),
            ),
            SizedBox(
              height: 60,
              width: 280,
              child: Container(
                padding: EdgeInsets.all(10),
                child: RaisedButton(
                  color: Colors.amber,
                  child: Text("Cadastrar"),
                    onPressed: () async {
                      await SdkSmarthouse.homeInstance;
                    },
                ),),
            )
        ],),
      ),
    );
  }
}
