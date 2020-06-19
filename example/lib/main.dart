import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:sdk_smarthouse/sdk_smarthouse.dart';
import 'package:sdk_smarthouse_example/home_screen.dart';
import 'package:sdk_smarthouse_example/wifi_screen.dart';

void main() {
  runApp(MaterialApp(
    home: MyApp(),
  ));
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
    try {
      await SdkSmarthouse.initTuya("p3xmfsppye34uynvund7", "fxedd9aqr4a77ds5hvurru4amn8n9934");
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

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
        body: SingleChildScrollView(
          child: Column(
          children: [
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
              child: Text("Registrar email"),
                onPressed: () async {
                  if(_emailController.text.isNotEmpty){
                   await SdkSmarthouse.getRegisterCode(_emailController.text, "55");
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
                      await SdkSmarthouse.createAccount(_validateCodeController.text, 
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
                  child: Text("Consultar dispositivos"),
                    onPressed: () async {
                      await SdkSmarthouse.homeInstance;
                      await SdkSmarthouse.configNetWork("ALHN-A733", "8296063960");
                      
                      
                    },
                ),),
            ),
            SizedBox(
              height: 60,
              width: 280,
              child: Container(
                padding: EdgeInsets.all(10),
                child: RaisedButton(
                  color: Colors.green,
                  child: Text("Interagir com Dispositivo"),
                    onPressed: () async {
                      Navigator.push(context, MaterialPageRoute( builder: (context) => WifiScreen()));
                    },
                ),),
            ),
        ],),
        )
      ),
    );
  }
}
