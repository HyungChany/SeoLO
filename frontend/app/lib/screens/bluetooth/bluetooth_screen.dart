import 'dart:async';
import 'dart:convert';

import 'package:app/main.dart';
import 'package:app/screens/bluetooth/bluetooth_off_screen.dart';
import 'package:app/view_models/core/core_check_view_model.dart';
import 'package:app/view_models/core/core_issue_view_model.dart';
import 'package:app/view_models/core/core_locked_view_model.dart';
import 'package:app/view_models/core/core_unlock_view_model.dart';
import 'package:app/widgets/bluetooth/scan_result_tile.dart';
import 'package:app/widgets/button/common_text_button.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:app/widgets/header/header.dart';
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
  BluetoothAdapterState _adapterState = BluetoothAdapterState.unknown;
  late StreamSubscription<BluetoothAdapterState> _adapterStateStateSubscription;
  final _storage = const FlutterSecureStorage();
  List<ScanResult> _scanResults = [];
  List<ScanResult> _lastScanResults = [];
  bool _isScanning = false;
  late StreamSubscription<List<ScanResult>> _scanResultsSubscription;
  late StreamSubscription<bool> _isScanningSubscription;
  List<String> _receivedValues = [];
  String? companyCode;
  String? coreCode;
  String? lockerToken;
  String? machineId;
  String? userId;
  String? lockerUid;
  String? lockerBattery;
  BluetoothService? bluetoothService;
  BluetoothCharacteristic? bluetoothCharacteristic;
  bool _isWriting = false;
  bool hasExecutedCoreIssued = false; // 함수 중복 방지
  bool hasExecutedCoreLocked = false;
  bool hasExecutedCoreUnlock = false;
  bool hasExecutedCoreCheck = false;
  bool hasExecutedAlert = false;

  @override
  void initState() {
    super.initState();

    _isWriting = false;
    hasExecutedCoreIssued = false;
    hasExecutedCoreLocked = false;
    hasExecutedCoreUnlock = false;
    hasExecutedCoreCheck = false;
    hasExecutedAlert = false;

    _adapterStateStateSubscription =
        FlutterBluePlus.adapterState.listen((state) {
      _adapterState = state;
      if (_adapterState != BluetoothAdapterState.on) {
        Navigator.pushReplacement(
            context,
            MaterialPageRoute(
                builder: (context) => const BluetoothOffScreen()));
      }
      if (mounted) {
        setState(() {});
      }
    });

    _scanResultsSubscription = FlutterBluePlus.scanResults.listen((results) {
      _scanResults = results;
      if (mounted) {
        setState(() {});
      }
    }, onError: (e) {
      debugPrint(e);
    });

    _isScanningSubscription = FlutterBluePlus.isScanning.listen((state) {
      _isScanning = state;
      if (mounted) {
        setState(() {});
      }
    });

    _lastScanResults = FlutterBluePlus.lastScanResults;
  }

  @override
  void dispose() {
    _scanResultsSubscription.cancel();
    _isScanningSubscription.cancel();
    _adapterStateStateSubscription.cancel();
    super.dispose();
  }

  Future onScanPressed() async {
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

  Future<void> connectToDevice(BluetoothDevice device) async {
    await device.connect();
    FlutterBluePlus.stopScan();
    await writeToDevice(device);
  }

  Future<void> writeToDevice(BluetoothDevice device) async {
    final issueVM = Provider.of<CoreIssueViewModel>(context, listen: false);
    final checkVM = Provider.of<CoreCheckViewModel>(context, listen: false);
    final lockedVM = Provider.of<CoreLockedViewModel>(context, listen: false);
    final unlockVM = Provider.of<CoreUnlockViewModel>(context, listen: false);
    setState(() {
      _isWriting = true;
    });
    await device.discoverServices().then((services) async {
      companyCode = await _storage.read(key: 'Company-Code');
      coreCode = await _storage.read(key: 'Core-Code');
      lockerToken = await _storage.read(key: 'locker_token');
      machineId = await _storage.read(key: 'machine_id');
      userId = await _storage.read(key: 'user_id');
      lockerUid = await _storage.read(key: 'locker_uid');
      lockerBattery = await _storage.read(key: 'locker_battery');
      // code가 write라면 init을 보냈는데 그 뒤에 꺼졌을 때
      if (coreCode == 'WRITE') {
        await _storage.delete(key: 'locker_uid');
        await _storage.delete(key: 'locker_battery');
        await _storage.write(key: 'Core-Code', value: 'INIT');
        setState(() {
          coreCode = 'INIT';
        });
      }
      if (coreCode == 'WRITED') {
        await _storage.write(key: 'Core-Code', value: 'INIT');
        setState(() {
          coreCode = 'INIT';
        });
      }
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
              //회사코드 토큰 장비 유저 uid coreCode
              String message =
                  "${companyCode ?? ''},${lockerToken ?? ''},${machineId ?? ''},${userId ?? ''},${lockerUid ?? ''},${coreCode ?? 'INIT'}";
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
                characteristic.lastValueStream.listen((value) async {
                  String receivedString = utf8.decode(value);
                  _receivedValues = receivedString.split(',');
                  // 응답 받으면 writing 중지
                  setState(() {
                    _isWriting = false;
                  });
                  if (_receivedValues[4] == userId) {
                    // 코드 uid 장비 배터리 유저id
                    debugPrint('응답값: $receivedString');
                    if (_receivedValues[0] == 'ALERT' && !hasExecutedAlert) {
                      setState(() {
                        _isWriting = false;
                      });
                      hasExecutedAlert = true;
                      showDialog(
                          context: context,
                          barrierDismissible: false,
                          builder: (BuildContext context) {
                            return CommonDialog(
                              content: '연결할 자물쇠를 확인해 주세요.',
                              buttonText: '확인',
                              buttonClick: () {
                                hasExecutedAlert = true;
                              },
                            );
                          });
                    } else {
                      await _storage.write(
                          key: 'Core-Code', value: _receivedValues[0]);
                      await _storage.write(
                          key: 'locker_uid', value: _receivedValues[1]);
                      await _storage.write(
                          key: 'machine_id', value: _receivedValues[2]);
                      await _storage.write(
                          key: 'locker_battery', value: _receivedValues[3]);
                      // 작업 내역을 미리 작성했다면
                      if (_receivedValues[0] == 'WRITED' &&
                          !hasExecutedCoreIssued) {
                        String? battery =
                            await _storage.read(key: 'locker_battery');
                        int? batteryInfo =
                            (battery != null) ? int.parse(battery) : 0;
                        issueVM.setBattery(batteryInfo);
                        String? lockerUid =
                            await _storage.read(key: 'locker_uid');
                        (lockerUid != null)
                            ? issueVM.setLockerUid(lockerUid)
                            : issueVM.setLockerUid('');
                        hasExecutedCoreIssued = true;
                        issueVM.coreIssue().then((_) {
                          if (issueVM.errorMessage == null) {
                            writeToDevice(device);
                          } else {
                            if (issueVM.errorMessage == 'JT') {
                              showDialog(
                                  context: context,
                                  barrierDismissible: false,
                                  builder: (BuildContext context) {
                                    return CommonDialog(
                                      content: '토큰이 만료되었습니다. 다시 로그인 해주세요.',
                                      buttonText: '확인',
                                      buttonClick: () {
                                        Navigator.pushNamedAndRemoveUntil(
                                            context,
                                            '/login',
                                            (route) => false);
                                      },
                                    );
                                  });
                            } else {
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return CommonDialog(
                                      content: issueVM.errorMessage!,
                                      buttonText: '확인',
                                    );
                                  });
                            }
                          }
                        });
                      }
                      if (_receivedValues[0] == 'WRITE') {
                        Navigator.pushReplacementNamed(context, '/checklist');
                      }
                      if (_receivedValues[0] == 'CHECK' &&
                          !hasExecutedCoreCheck) {
                        hasExecutedCoreCheck = true;
                        checkVM.coreCheck().then((_) async {
                          if (checkVM.errorMessage == null) {
                            if (coreCode != null) {
                              await _storage.write(
                                  key: 'Core-Code', value: coreCode);
                            } else {
                              await _storage.write(
                                  key: 'Core-Code', value: coreCode);
                            }
                            if (lockerUid != null) {
                              await _storage.write(
                                  key: 'locker_uid', value: lockerUid);
                            } else {
                              await _storage.delete(key: 'locker_uid');
                            }
                            if (lockerBattery != null) {
                              await _storage.write(
                                  key: 'locker_battery', value: lockerBattery);
                            } else {
                              await _storage.delete(key: 'locker_battery');
                            }
                            if (machineId != null) {
                              await _storage.write(
                                  key: 'machine_id', value: machineId);
                            } else {
                              await _storage.delete(key: 'machine_id');
                            }

                            await Navigator.pushReplacementNamed(
                                context, '/otherWorklistCheck');
                          } else {
                            if (checkVM.errorMessage == 'JT') {
                              showDialog(
                                  context: context,
                                  barrierDismissible: false,
                                  builder: (BuildContext context) {
                                    return CommonDialog(
                                      content: '토큰이 만료되었습니다. 다시 로그인 해주세요.',
                                      buttonText: '확인',
                                      buttonClick: () {
                                        Navigator.pushNamedAndRemoveUntil(
                                            context,
                                            '/login',
                                            (route) => false);
                                      },
                                    );
                                  });
                            } else {
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return CommonDialog(
                                      content: checkVM.errorMessage!,
                                      buttonText: '확인',
                                      buttonClick: () {
                                        hasExecutedCoreCheck = false;
                                      },
                                    );
                                  });
                            }
                          }
                        });
                      }
                      if (_receivedValues[0] == 'UNLOCK' &&
                          !hasExecutedCoreUnlock) {
                        hasExecutedCoreUnlock = true;
                        unlockVM.coreUnlock().then((_) {
                          if (unlockVM.errorMessage == null) {
                            Navigator.pushNamedAndRemoveUntil(
                              context,
                              '/resultUnlock',
                              (Route<dynamic> route) => route.isFirst,
                            );
                          } else {
                            if (unlockVM.errorMessage == 'JT') {
                              showDialog(
                                  context: context,
                                  barrierDismissible: false,
                                  builder: (BuildContext context) {
                                    return CommonDialog(
                                      content: '토큰이 만료되었습니다. 다시 로그인 해주세요.',
                                      buttonText: '확인',
                                      buttonClick: () {
                                        Navigator.pushNamedAndRemoveUntil(
                                            context,
                                            '/login',
                                            (route) => false);
                                      },
                                    );
                                  });
                            } else {
                              Navigator.pushNamedAndRemoveUntil(
                                context,
                                '/resultUnlock',
                                (Route<dynamic> route) => route.isFirst,
                              );
                            }
                          }
                        });
                      }
                      if (_receivedValues[0] == 'LOCKED' &&
                          !hasExecutedCoreLocked) {
                        hasExecutedCoreLocked = true;
                        lockedVM.coreLocked().then((_) {
                          if (lockedVM.errorMessage == null) {
                            Navigator.pushNamedAndRemoveUntil(
                              context,
                              '/resultLock',
                              (Route<dynamic> route) => route.isFirst,
                            );
                          } else {
                            if (lockedVM.errorMessage == 'JT') {
                              showDialog(
                                  context: context,
                                  barrierDismissible: false,
                                  builder: (BuildContext context) {
                                    return CommonDialog(
                                      content: '토큰이 만료되었습니다. 다시 로그인 해주세요.',
                                      buttonText: '확인',
                                      buttonClick: () {
                                        Navigator.pushNamedAndRemoveUntil(
                                            context,
                                            '/login',
                                            (route) => false);
                                      },
                                    );
                                  });
                            } else {
                              Navigator.pushNamedAndRemoveUntil(
                                context,
                                '/resultLock',
                                (Route<dynamic> route) => route.isFirst,
                              );
                            }
                          }
                        });
                      }
                    }
                  }
                });
              } catch (e) {
                setState(() {
                  _isWriting = false;
                });
                showDialog(
                    context: context,
                    barrierDismissible: false,
                    builder: (BuildContext context) {
                      return CommonDialog(
                        content: '다시 연결해 주세요.',
                        buttonText: '확인',
                      );
                    });
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
      return CommonTextButton(text: '검색 중지', onTap: onStopPressed);
    } else {
      return CommonTextButton(text: '연결 가능한 자물쇠 찾기', onTap: onScanPressed);
    }
  }

  List<Widget> _buildScanResultTiles(BuildContext context) {
    return _scanResults
        .map(
          (r) => ScanResultTile(
            result: r,
            onTap: () {
              connectToDevice(r.device);
            },
          ),
        )
        .toList();
  }

  List<Widget> _buildLastScanResultTiles(BuildContext context) {
    return _lastScanResults
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
      appBar: const Header(title: '자물쇠 선택', back: true),
      body: (_isWriting == true)
          ? Center(
              child: Image.asset(
              'assets/images/loading_icon.gif',
              width: 200,
              height: 200,
            ))
          : RefreshIndicator(
              onRefresh: onRefresh,
              child: Stack(
                children: [
                  Padding(
                    padding: const EdgeInsets.all(10.0),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.start,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        Text('최근 연결한 자물쇠',
                            style: TextStyle(
                                fontSize: 20, fontWeight: FontWeight.bold)),
                        SizedBox(
                          height: 20,
                        ),
                        Expanded(
                          flex: 2,
                          child: SingleChildScrollView(
                            child: Column(
                              children: _buildLastScanResultTiles(context),
                            ),
                          ),
                        ),
                        SizedBox(
                          height: 20,
                        ),
                        Text('새로운 자물쇠',
                            style: TextStyle(
                                fontSize: 20, fontWeight: FontWeight.bold)),
                        SizedBox(
                          height: 20,
                        ),
                        Expanded(
                          flex: 8,
                          child: SingleChildScrollView(
                            child: Column(
                              children: _buildScanResultTiles(context),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                  Align(
                      alignment: Alignment.bottomCenter,
                      child: Padding(
                          padding: const EdgeInsets.only(
                              bottom: 20, left: 50, right: 50),
                          child: buildScanButton(context)))
                ],
              ),
            ),
    );
  }
}
