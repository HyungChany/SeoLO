import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class LoadingUnlockScreen extends StatefulWidget {
  const LoadingUnlockScreen({super.key});

  @override
  State<LoadingUnlockScreen> createState() => _LoadingUnlockScreenState();
}

class _LoadingUnlockScreenState extends State<LoadingUnlockScreen> {
  final _storage = const FlutterSecureStorage();
  List<BluetoothDevice> _systemDevices = [];
  List<String> _receivedValues = [];

  @override
  void initState() {
    super.initState();
    onScan();
    connectToDevice(_systemDevices.last);
  }

  Future onScan() async {
    try {
      _systemDevices = await FlutterBluePlus.systemDevices;
    } catch (e) {
      debugPrint("System Devices Error: $e");
    }
    try {
      await FlutterBluePlus.startScan(
          timeout: const Duration(seconds: 5), withKeywords: ["SEOLO"]);
    } catch (e) {
      debugPrint("Start Scan Error: $e");
    }
    if (mounted) {
      setState(() {});
    }
  }

  void connectToDevice(BluetoothDevice device) async {
    String? companyCode = await _storage.read(key: 'Company-Code');
    String? coreCode = await _storage.read(key: 'Core-Code');
    String? lockerToken = await _storage.read(key: 'locker_token');
    String? machineId = await _storage.read(key: 'machine_id');
    String? userID = await _storage.read(key: 'user_id');

    await device.connect();
    debugPrint('Connected to ${device.platformName}');
    device.discoverServices().then((services) async {
      for (var service in services) {
        // debugPrint('service uuids : ${service.uuid.toString().toUpperCase()}');
        if (service.uuid.toString().toUpperCase() ==
            "20240520-C104-C104-C104-012345678910") {
          List<BluetoothCharacteristic> characteristics =
              service.characteristics;
          for (var characteristic in characteristics) {
            if (characteristic.uuid.toString().toUpperCase() ==
                "20240521-C104-C104-C104-012345678910") {
              // debugPrint('character uuids : ${characteristic.uuid.toString()}');
              // characteristic = characteristic;
              debugPrint('쓰기 시도');
              // String message = "SFY001KOR,LOCK,token,15,3";
              String message =
                  "${companyCode ?? ''},${coreCode ?? 'INIT'},${lockerToken ?? ''},${machineId ?? '4'},${userID ?? ''}";
              List<int> encodedMessage = utf8.encode(message);
              try {
                await characteristic.write(encodedMessage,
                    // withoutResponse:
                    // characteristic.properties.writeWithoutResponse,
                    allowLongWrite: true,
                    timeout: 30);
                debugPrint('write success');
                characteristic.setNotifyValue(true);
                characteristic.read();
                characteristic.lastValueStream.listen((value) {
                  String receivedString = utf8.decode(value);
                  debugPrint('응답: $receivedString');
                  _receivedValues = receivedString.split(',');
                  debugPrint(_receivedValues[0]);
                  if (_receivedValues[4] == userID) {
                    setState(() {
                      _storage.write(
                          key: 'Core-Code', value: _receivedValues[0]);
                      _storage.write(
                          key: 'locker_uid', value: _receivedValues[1]);
                      _storage.write(
                          key: 'machine_id', value: _receivedValues[2]);
                      _storage.write(
                          key: 'locker_battery', value: _receivedValues[3]);
                    });
                    if (_receivedValues[0] == 'UNLOCK') {
                      Navigator.pushNamedAndRemoveUntil(
                          context, '/resultUnlock', (route) => false);
                    }
                    // if (characteristic.properties.read) {
                    //   await characteristic.read();
                    //   debugPrint('응답값: ${characteristic.read().toString()}');
                    // }
                  }
                });
              } catch (e) {
                debugPrint('write error: $e');
              }
            }
          }
        }
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
          child: Image.asset(
        'assets/images/loading_icon.gif',
        width: 200,
        height: 200,
      )),
    );
  }
}
