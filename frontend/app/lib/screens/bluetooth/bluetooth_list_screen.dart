// import 'dart:async';
//
// import 'package:flutter/material.dart';
// import 'package:flutter_blue_plus/flutter_blue_plus.dart';
//
// import 'device_screen.dart';
// import '../utils/snackbar.dart';
// import '../widgets/system_device_tile.dart';
// import '../widgets/scan_result_tile.dart';
// import '../utils/extra.dart';
//
// class BluetoothListScreen extends StatefulWidget {
//   const BluetoothListScreen({super.key});
//
//   @override
//   State<BluetoothListScreen> createState() => _BluetoothListScreenState();
// }
//
// class _BluetoothListScreenState extends State<BluetoothListScreen> {
//   List<BluetoothDevice> _systemDevices = [];
//   List<ScanResult> _scanResults = [];
//   List<ScanResult> _lastScanResults = [];
//   bool _isScanning = false;
//   late StreamSubscription<List<ScanResult>> _scanResultsSubscription;
//   late StreamSubscription<bool> _isScanningSubscription;
//
//   @override
//   void initState() {
//     super.initState();
//     _scanResultsSubscription = FlutterBluePlus.scanResults.listen((results) {
//       _scanResults = results;
//       if (mounted) {
//         setState(() {});
//       }
//     }, onError: (e) {
//       debugPrint(e);
//     });
//
//     _isScanningSubscription = FlutterBluePlus.isScanning.listen((state) {
//       _isScanning = state;
//       if (mounted) {
//         setState(() {});
//       }
//     });
//
//     _lastScanResults = FlutterBluePlus.lastScanResults;
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
//       debugPrint("System Devices Error:, $e");
//     }
//     try {
//       await FlutterBluePlus.startScan(timeout: const Duration(seconds: 5), withKeywords: ["SEOLO LOCK"]);
//     } catch (e) {
//       debugPrint("Start Scan Error:, $e");
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
//   void onConnectPressed(BluetoothDevice device) {
//     device.connect();
//     MaterialPageRoute route = MaterialPageRoute(
//         builder: (context) => DeviceScreen(device: device), settings: RouteSettings(name: '/DeviceScreen'));
//     Navigator.of(context).push(route);
//     FlutterBluePlus.stopScan();
//   }
//
//   Future onRefresh() {
//     if (_isScanning == false) {
//       FlutterBluePlus.startScan(timeout: const Duration(seconds: 15),withKeywords: ["SEOLO LOCK"]);
//     }
//     if (mounted) {
//       setState(() {});
//     }
//     return Future.delayed(Duration(milliseconds: 500));
//   }
//
//   Widget buildScanButton(BuildContext context) {
//     if (FlutterBluePlus.isScanningNow) {
//       return FloatingActionButton(
//         child: const Icon(Icons.stop),
//         onPressed: onStopPressed,
//         backgroundColor: Colors.red,
//       );
//     } else {
//       return FloatingActionButton(child: const Text("SCAN"), onPressed: onScanPressed);
//     }
//   }
//
//   List<Widget> _buildSystemDeviceTiles(BuildContext context) {
//     return _systemDevices
//         .map(
//           (d) => SystemDeviceTile(
//         device: d,
//         onOpen: () => Navigator.of(context).push(
//           MaterialPageRoute(
//             builder: (context) => DeviceScreen(device: d),
//             settings: RouteSettings(name: '/DeviceScreen'),
//           ),
//         ),
//         onConnect: () => onConnectPressed(d),
//       ),
//     )
//         .toList();
//   }
//
//   List<Widget> _buildScanResultTiles(BuildContext context) {
//     return _scanResults
//         .map(
//           (r) => ScanResultTile(
//         result: r,
//         onTap: () => onConnectPressed(r.device),
//       ),
//     )
//         .toList();
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     return ScaffoldMessenger(
//       key: Snackbar.snackBarKeyB,
//       child: Scaffold(
//         appBar: AppBar(
//           title: const Text('Find Devices'),
//         ),
//         body: RefreshIndicator(
//           onRefresh: onRefresh,
//           child: ListView(
//             children: <Widget>[
//               Text('system'),
//               ..._buildSystemDeviceTiles(context),
//               Text('scan'),
//               ..._buildScanResultTiles(context),
//             ],
//           ),
//         ),
//         floatingActionButton: buildScanButton(context),
//       ),
//     );
//   }
// }
