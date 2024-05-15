import 'dart:async';
import 'dart:convert';

import 'package:app/view_models/core/core_check_view_model.dart';
import 'package:app/view_models/core/core_issue_view_model.dart';
import 'package:app/view_models/core/core_locked_view_model.dart';
import 'package:app/view_models/core/core_unlock_view_model.dart';
import 'package:app/widgets/bluetooth/scan_result_tile.dart';
import 'package:app/widgets/bluetooth/system_device_tile.dart';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

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
  String? companyCode;
  String? coreCode;
  String? lockerToken;
  String? machineId;
  String? userId;
  BluetoothService? bluetoothService;
  BluetoothCharacteristic? bluetoothCharacteristic;

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
    await _storage.delete(key: 'Core-Code');
    await _storage.delete(key: 'machine_id');
    await _storage.delete(key: 'locker_uid');
    await _storage.delete(key: 'locker_token');
    await _storage.delete(key: 'locker_battery');
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

  Future onStopPressed() async {
    try {
      FlutterBluePlus.stopScan();
    } catch (e) {
      debugPrint("Stop Scan Error: $e");
    }
  }

  void connectToDevice(BluetoothDevice device) async {
    await device.connect();
    FlutterBluePlus.stopScan();
    writeToDevice(device);
  }


  void writeToDevice(BluetoothDevice device) async {
    final issueVM = Provider.of<CoreIssueViewModel>(context, listen: false);
    final lockedVM = Provider.of<CoreLockedViewModel>(context, listen: false);
    // debugPrint('Connected to ${device.platformName}');
    device.discoverServices().then((services) async {
      companyCode = await _storage.read(key: 'Company-Code');
      coreCode = await _storage.read(key: 'Core-Code');
      lockerToken = await _storage.read(key: 'locker_token');
      machineId = await _storage.read(key: 'machine_id');
      userId = await _storage.read(key: 'user_id');
      for (var service in services) {
        if (service.uuid.toString().toUpperCase() ==
            "20240520-C104-C104-C104-012345678910") {
          bluetoothService = service;
          List<BluetoothCharacteristic> characteristics =
              service.characteristics;
          for (var characteristic in characteristics) {
            if (characteristic.uuid.toString().toUpperCase() ==
                "20240521-C104-C104-C104-012345678910") {
              bluetoothCharacteristic = characteristic;
              debugPrint('쓰기 시도');
              String message =
                  "${companyCode ?? ''},${coreCode ?? 'INIT'},${lockerToken ?? ''},${machineId ?? ''},${userId ?? ''}";
              debugPrint('보내는 값: $message');
              List<int> encodedMessage = utf8.encode(message);
              try {
                await characteristic.write(encodedMessage,
                    // withoutResponse:
                    // characteristic.properties.writeWithoutResponse,
                    allowLongWrite: true,
                    timeout: 30);
                characteristic.setNotifyValue(true);
                characteristic.read();
                characteristic.lastValueStream.listen((value) {
                  String receivedString = utf8.decode(value);
                  _receivedValues = receivedString.split(',');
                  if (_receivedValues[4] == userId) {
                    _storage.write(key: 'Core-Code', value: _receivedValues[0]);
                    _storage.write(key: 'locker_uid', value: _receivedValues[1]);
                    _storage.write(key: 'machine_id', value: _receivedValues[2]);
                    _storage.write(key: 'locker_battery', value: _receivedValues[3]);
                  }
                  // 작업 내역 먼저 작성하면 machine id 저장되어있는 상태
                  // 작업 내역 작성하고 확인 누르면 블투 연결부터 잠금까지 한번에
                  if (_receivedValues[0] == 'WRITED') {
                    issueVM.coreIssue().then((_) {
                      // ISSUE API 성공하면 바로 LOCK
                      writeToDevice(device);
                    });
                  }
                  if (_receivedValues[0] == 'WRITE') {
                    Navigator.pushReplacementNamed(context, '/checklist');
                  }
                  if (_receivedValues[0] == 'CHECK') {
                    Navigator.pushReplacementNamed(
                        context, '/otherWorklistCheck');
                  }
                  if (_receivedValues[0] == 'UNLOCK') {
                    Navigator.pushNamedAndRemoveUntil(
                        context, '/resultUnlock', (route) => false);
                  }
                  if (_receivedValues[0] == 'LOCKED') {

                    lockedVM.coreLocked().then((_) {
                      Navigator.pushNamedAndRemoveUntil(
                          context, '/main', (route) => false);
                    });
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

  Future onRefresh() {
    if (_isScanning == false) {
      FlutterBluePlus.startScan(
          timeout: const Duration(seconds: 5), withKeywords: ["SEOLO"]);
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
