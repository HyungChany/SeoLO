import 'dart:async';
import 'dart:convert';

import 'package:app/widgets/bluetooth/scan_result_tile.dart';
import 'package:app/widgets/bluetooth/system_device_tile.dart';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class BluetoothScreen extends StatefulWidget {
  const BluetoothScreen({super.key});

  @override
  State<BluetoothScreen> createState() => _BluetoothScreenState();
}

class _BluetoothScreenState extends State<BluetoothScreen> {
  final _storage = const FlutterSecureStorage();
  List<BluetoothDevice> _systemDevices = [];
  List<ScanResult> _scanResults = [];
  bool _isScanning = false;
  late StreamSubscription<List<ScanResult>> _scanResultsSubscription;
  late StreamSubscription<bool> _isScanningSubscription;
  List<String> _receivedValues = [];

  @override
  @override
  void initState() {
    super.initState();

    _scanResultsSubscription = FlutterBluePlus.scanResults.listen((results) {
      _scanResults = results;
      if (mounted) {
        setState(() {});
      }
    }, onError: (e) {
      debugPrint("Scan Error: $e");
    });

    _isScanningSubscription = FlutterBluePlus.isScanning.listen((state) {
      _isScanning = state;
      if (mounted) {
        setState(() {});
      }
    });
  }

  @override
  void dispose() {
    _scanResultsSubscription.cancel();
    _isScanningSubscription.cancel();
    super.dispose();
  }

  Future onScanPressed() async {
    try {
      _systemDevices = await FlutterBluePlus.systemDevices;
    } catch (e) {
      debugPrint("System Devices Error: $e");
    }
    try {
      await FlutterBluePlus.startScan(
          timeout: const Duration(seconds: 15), withKeywords: ["SEOLO"]);
    } catch (e) {
      debugPrint("Start Scan Error: $e");
    }
    if (mounted) {
      setState(() {});
    }
  }

  Future onStopPressed() async {
    try {
      FlutterBluePlus.stopScan();
    } catch (e) {
      debugPrint("Stop Scan Error: $e");
    }
  }

  void connectToDevice(BluetoothDevice device) async {
    String? companyCode = await _storage.read(key: 'Company-Code');
    String? coreCode = await _storage.read(key: 'Core-Code');
    String? lockerToken = await _storage.read(key: 'locker_token');
    String? machineId = await _storage.read(key: 'machine_id');

    await device.connect();
    debugPrint('Connected to ${device.platformName}');
    device.discoverServices().then((services) async {
      for (var service in services) {
        // debugPrint('service uuids : ${service.uuid.toString().toUpperCase()}');
        if (service.uuid.toString().toUpperCase() == '19B1') {
          List<BluetoothCharacteristic> characteristics =
              service.characteristics;
          for (var characteristic in characteristics) {
            if (characteristic.uuid.toString().toUpperCase() == '19B2') {
              // debugPrint('character uuids : ${characteristic.uuid.toString()}');
              // characteristic = characteristic;
              debugPrint('쓰기 시도');
              String message =
                  "${companyCode ?? ''},${coreCode ?? ''},${lockerToken ?? ''},${machineId ?? ''}";
              List<int> encodedMessage = utf8.encode(message);
              try {
                await characteristic.write(encodedMessage,
                    // withoutResponse:
                    // characteristic.properties.writeWithoutResponse,
                    allowLongWrite: true,
                    timeout: 30);
                debugPrint('write success');
                characteristic.setNotifyValue(true);
                characteristic.lastValueStream.listen((value) {
                  setState(() {
                    String receivedString = utf8.decode(value);
                    _receivedValues = receivedString.split(',');
                    _storage.write(key: 'Core-Code', value: _receivedValues[0]);
                    _storage.write(
                        key: 'locker_uid', value: _receivedValues[1]);
                    _storage.write(
                        key: 'machine_id', value: _receivedValues[2]);
                    _storage.write(
                        key: 'locker_token', value: _receivedValues[3]);
                    _storage.write(
                        key: 'locker_battery', value: _receivedValues[4]);
                  });
                  // if (characteristic.properties.read) {
                  //   await characteristic.read();
                  //   debugPrint('응답값: ${characteristic.read().toString()}');
                  // }
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

  Future onRefresh() {
    if (_isScanning == false) {
      FlutterBluePlus.startScan(
          timeout: const Duration(seconds: 15), withKeywords: ["SEOLO"]);
    }
    if (mounted) {
      setState(() {});
    }
    return Future.delayed(const Duration(milliseconds: 500));
  }

  Widget buildScanButton(BuildContext context) {
    if (FlutterBluePlus.isScanningNow) {
      return FloatingActionButton(
        onPressed: onStopPressed,
        backgroundColor: Colors.red,
        child: const Icon(Icons.stop),
      );
    } else {
      return FloatingActionButton(
          onPressed: onScanPressed, child: const Text("SCAN"));
    }
  }

  List<Widget> _buildSystemDeviceTiles(BuildContext context) {
    return _systemDevices
        .map(
          (d) => SystemDeviceTile(
            device: d,
            onOpen: () {},
            onConnect: () => connectToDevice(d),
          ),
        )
        .toList();
  }

  List<Widget> _buildScanResultTiles(BuildContext context) {
    return _scanResults
        .map(
          (r) => ScanResultTile(
            result: r,
            onTap: () => connectToDevice(r.device),
          ),
        )
        .toList();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: RefreshIndicator(
        onRefresh: onRefresh,
        child: ListView(
          children: <Widget>[
            Text('receive: $_receivedValues'),
            Text('system'),
            ..._buildSystemDeviceTiles(context),
            Text('scan'),
            ..._buildScanResultTiles(context),
          ],
        ),
      ),
      floatingActionButton: buildScanButton(context),
    );
  }
}
