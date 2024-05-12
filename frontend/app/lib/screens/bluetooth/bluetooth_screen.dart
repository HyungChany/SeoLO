// import 'dart:async';
// import 'dart:convert';
// import 'dart:typed_data';
//
// import 'package:app/utils/extra.dart';
// import 'package:app/widgets/bluetooth/scan_result_tile.dart';
// import 'package:app/widgets/bluetooth/service_tile.dart';
// import 'package:app/widgets/bluetooth/system_device_tile.dart';
// import 'package:flutter/material.dart';
// import 'package:flutter_blue_plus/flutter_blue_plus.dart';
//
// class BluetoothScreen extends StatefulWidget {
//   const BluetoothScreen({super.key});
//
//   @override
//   State<BluetoothScreen> createState() => _BluetoothScreenState();
// }
//
// class _BluetoothScreenState extends State<BluetoothScreen> {
//   late BluetoothDevice _connectedDevice;
//   List<BluetoothDevice> _systemDevices = [];
//   List<ScanResult> _scanResults = [];
//   bool _isScanning = false;
//   bool _isDiscoveringServices = false;
//   List<BluetoothService> _services = [];
//   late StreamSubscription<List<ScanResult>> _scanResultsSubscription;
//   late StreamSubscription<bool> _isScanningSubscription;
//   late BluetoothCharacteristic characteristic;
//   String receivedData = '';
//
//   @override
//   @override
//   void initState() {
//     super.initState();
//
//     _scanResultsSubscription = FlutterBluePlus.scanResults.listen((results) {
//       _scanResults = results;
//       if (mounted) {
//         setState(() {});
//       }
//     }, onError: (e) {
//       debugPrint("Scan Error: $e");
//     });
//
//     _isScanningSubscription = FlutterBluePlus.isScanning.listen((state) {
//       _isScanning = state;
//       if (mounted) {
//         setState(() {});
//       }
//     });
//   }
//
//   @override
//   void dispose() {
//     _scanResultsSubscription.cancel();
//     _isScanningSubscription.cancel();
//     super.dispose();
//   }
//
//   Future onScanPressed() async {
//     try {
//       _systemDevices = await FlutterBluePlus.systemDevices;
//     } catch (e) {
//       debugPrint("System Devices Error: $e");
//     }
//     try {
//       await FlutterBluePlus.startScan(
//           timeout: const Duration(seconds: 15));
//     } catch (e) {
//       debugPrint("Start Scan Error: $e");
//     }
//     if (mounted) {
//       setState(() {});
//     }
//   }
//
//   Future onStopPressed() async {
//     try {
//       FlutterBluePlus.stopScan();
//     } catch (e) {
//       debugPrint("Stop Scan Error: $e");
//     }
//   }
//
//   void connectToDevice(BluetoothDevice device) async {
//     await device.connect();
//     debugPrint('Connected to ${device.platformName}');
//     device.discoverServices().then((services) {
//       for (var service in services) {
//         for (var characteristic in service.characteristics) {
//           if (characteristic.uuid.toString() == '19B10000-E8F2-537E-4F6C-D104768A1214') {
//             this.characteristic = characteristic;
//             sendDataAndReceiveResponse(device);
//           }
//         }
//       }
//     });
//     }
//
//   void sendDataAndReceiveResponse(device) async {
//     if (device != null) {
//       List<int> bytesToSend = utf8.encode(jsonEncode(['a', 'b', 1, 1]));
//       characteristic.write(bytesToSend);
//       device.setNotifyValue(characteristic, true);
//       characteristic.value.listen((value) {
//         setState(() {
//           receivedData = utf8.decode(value);
//           debugPrint('receivedData: $receivedData');
//         });
//       });
//     }
//   }
//
//   void onConnectPressed(BluetoothDevice device) {
//     device.connectAndUpdateStream().catchError((e) {
//       debugPrint("Connect Error: $e");
//     });
//     debugPrint('연결 성공');
//     onDiscoverServicesPressed(device);
//   }
//
//   Future onDiscoverServicesPressed(device) async {
//     if (mounted) {
//       setState(() {
//         _isDiscoveringServices = true;
//       });
//     }
//     try {
//       _services = await device.discoverServices();
//       debugPrint("Discover Services: Success");
//       sendDataAndReceiveResponse(device);
//     } catch (e) {
//       debugPrint("Discover Services Error: $e");
//     }
//     if (mounted) {
//       setState(() {
//         _isDiscoveringServices = false;
//       });
//     }
//   }
//
//   Future onRefresh() {
//     if (_isScanning == false) {
//       FlutterBluePlus.startScan(
//           timeout: const Duration(seconds: 15));
//     }
//     if (mounted) {
//       setState(() {});
//     }
//     return Future.delayed(const Duration(milliseconds: 500));
//   }
//
//   Widget buildScanButton(BuildContext context) {
//     if (FlutterBluePlus.isScanningNow) {
//       return FloatingActionButton(
//         onPressed: onStopPressed,
//         backgroundColor: Colors.red,
//         child: const Icon(Icons.stop),
//       );
//     } else {
//       return FloatingActionButton(
//           onPressed: onScanPressed, child: const Text("SCAN"));
//     }
//   }
//
//   List<Widget> _buildSystemDeviceTiles(BuildContext context) {
//     return _systemDevices
//         .map(
//           (d) => SystemDeviceTile(
//             device: d,
//             onOpen: () {},
//             onConnect: () => onConnectPressed(d),
//           ),
//         )
//         .toList();
//   }
//
//   List<Widget> _buildScanResultTiles(BuildContext context) {
//     return _scanResults
//         .map(
//           (r) => ScanResultTile(
//             result: r,
//             onTap: () => onConnectPressed(r.device),
//           ),
//         )
//         .toList();
//   }
//
//   List<Widget> _buildServiceTiles(BuildContext context, BluetoothDevice d) {
//     return _services
//         .map(
//           (s) => ServiceTile(
//         service: s,
//         characteristicTiles: s.characteristics.map((c) => _buildCharacteristicTile(c)).toList(),
//       ),
//     )
//         .toList();
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       body: RefreshIndicator(
//         onRefresh: onRefresh,
//         child: ListView(
//           children: <Widget>[
//             Text('receive: $receivedData'),
//             Text('system'),
//             ..._buildSystemDeviceTiles(context),
//             Text('scan'),
//             ..._buildScanResultTiles(context),
//             Text('service'),
//             ..._buildServiceTiles(context, widget.device),
//           ],
//         ),
//       ),
//       floatingActionButton: buildScanButton(context),
//     );
//   }
// }
