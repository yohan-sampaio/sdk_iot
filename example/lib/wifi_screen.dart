import 'package:flutter/material.dart';
import 'package:sdk_smarthouse/sdk_smarthouse.dart';

class WifiScreen extends StatefulWidget {
  @override
  _WifiScreenState createState() => _WifiScreenState();
}

class _WifiScreenState extends State<WifiScreen> {
  final TextEditingController _devIdController = TextEditingController();
  List<dynamic> listWifi ;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Plugin example app'),),
      body: Column(
        children: [
          Padding(
            padding: EdgeInsets.all(10),
            child: TextField(
              controller: _devIdController,
              decoration: InputDecoration(
                labelText: "Id da Lampada",
              ),
            ),
          ),
          SizedBox(
              height: 60,
              width: 280,
              child: Container(
                padding: EdgeInsets.all(10),
                child: RaisedButton(
                  color: Colors.green,
                  child: Text("Ligar Lampada"),
                    onPressed: () async {
                      if(_devIdController.text.isNotEmpty){
                        await SdkSmarthouse.turnOnLamp(_devIdController.text);
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
                  color: Colors.orangeAccent,
                  child: Text("Desligar Lampada"),
                    onPressed: () async {
                      if(_devIdController.text.isNotEmpty){
                        await SdkSmarthouse.turnOffLamp(_devIdController.text);
                      }
                    },
                ),
              ),
            )
        ],
      ),
    );
  }
}