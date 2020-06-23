import 'package:flutter/material.dart';
import 'package:sdk_smarthouse/sdk_smarthouse.dart';

class HomeCreate extends StatefulWidget {
  @override
  _HomeCreateState createState() => _HomeCreateState();
}

class _HomeCreateState extends State<HomeCreate> {
  final TextEditingController _nomeHomeController = TextEditingController();
  final TextEditingController _geoNomHomeController = TextEditingController();
  final TextEditingController _latHomeController = TextEditingController();
  final TextEditingController _lonHomeController = TextEditingController();
  final TextEditingController _roomHomeController = TextEditingController();
  GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  double lat,lon;
  String nameHome, geoNameHome;
  List<String> listRooms = List<String>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Plugin example app'),),
      body: SingleChildScrollView(
        child: Form(
          key: _formKey,
          child: Column(
            children: [
          Padding(
            padding: EdgeInsets.all(10),
            child: TextFormField(
              controller: _nomeHomeController,
              decoration: InputDecoration(
                labelText: "Nome Casa",
              ),
              validator: (value){
                if(value.isEmpty){
                  
                }
              },
            ),
          ),
          Padding(
            padding: EdgeInsets.all(10),
            child: TextFormField(
              controller: _geoNomHomeController,
              decoration: InputDecoration(
                labelText: "Geo Home Nome",
              ),
              validator: (value){
                if(value.isEmpty){
                
                }
              },
            ),
          ),
          Padding(
            padding: EdgeInsets.all(10),
            child: TextFormField(
              controller: _latHomeController,
              decoration: InputDecoration(
                labelText: "Latitude",
              ),
              keyboardType: TextInputType.numberWithOptions(decimal:true, signed:true),
              validator: (value){
                if(value.isEmpty){
                  
                }
              },
            ),
          ),
          Padding(
            padding: EdgeInsets.all(10),
            child: TextFormField(
              controller: _lonHomeController,
              decoration: InputDecoration(
                labelText: "Latitude",
              ),
              keyboardType: TextInputType.numberWithOptions(decimal:true, signed:true),
              validator: (value){
                if(value.isEmpty){
                  
                }
              },
            ),
          ),
          Row(
            children: [
              Expanded(
                child:Padding(
                padding: EdgeInsets.all(10),
                child: TextField(
                controller: _roomHomeController,
                decoration: InputDecoration(
                  labelText: "Quarto Nome",
                  ),
                ),
              ),),
              IconButton(
                icon: Icon(Icons.add),
                onPressed: (){
                    if(_roomHomeController.text.isNotEmpty){
                      listRooms.add(_roomHomeController.text);
                      setState(() {
                        _roomHomeController.text = "";
                      });
                    }
                },
              )
          ],),
          SizedBox(
              height: 60,
              width: 280,
              child: Container(
                padding: EdgeInsets.all(10),
                child: RaisedButton(
                  color: Colors.amber,
                  child: Text("Consultar Casa"),
                    onPressed: () async {
                      if(_formKey.currentState.validate()){
                        if(listRooms.isNotEmpty){
                          nameHome = _nomeHomeController.text;
                          lat = double.parse(_latHomeController.text);
                          lon = double.parse(_lonHomeController.text);
                          await SdkSmarthouse.createHome(nameHome
                          , geoNameHome, lon, lat, listRooms);
                        }
                      }
                    },
                ),),
            ),
        ],),)
      ),
    );
  }
}