import 'dart:io';

import 'package:app/main.dart';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

class BluetoothScreen extends StatefulWidget {

  const BluetoothScreen({super.key});

  @override
  State<BluetoothScreen> createState() => _BluetoothScreenState();
}

class _BluetoothScreenState extends State<BluetoothScreen> {
  FlutterBluePlus flutterBlue = FlutterBluePlus();
  List<BluetoothDevice> connectedDevices = [];
  bool isScanning = false;
  List<ScanResult> scanResults = [];

  @override
  void initState() {
    super.initState();
    // TODO: Connect to previously connected device (if any)
    checkBluetoothAvailability();
  }

  void checkBluetoothAvailability() async {
    if (await FlutterBluePlus.isSupported == false) {
      debugPrint("Bluetooth not supported by this device");
      return;
    }

    var subscription =
        FlutterBluePlus.adapterState.listen((BluetoothAdapterState state) {
      if (state == BluetoothAdapterState.on) {
        getScanForDevice();
      } else {}
    });

    if (Platform.isAndroid) {
      await FlutterBluePlus.turnOn();
    }

    subscription.cancel();
  }

  void getScanForDevice() async {
    onStartScan();
    var subscription = FlutterBluePlus.onScanResults.listen(
      (results) {
        if (results.isNotEmpty) {
          ScanResult r = results.last;
          scanResults.add(r);
          debugPrint(
              '${r.device.remoteId}: "${r.advertisementData.advName}" found!');
        }
      },
      onError: (e) => debugPrint(e),
    );

    FlutterBluePlus.cancelWhenScanComplete(subscription);

    await FlutterBluePlus.adapterState
        .where((val) => val == BluetoothAdapterState.on)
        .first;


    await FlutterBluePlus.isScanning.where((val) => val == false).first;
  }

  Future onStartScan() async {
    int divisor = Platform.isAndroid ? 8 : 1;
    await FlutterBluePlus.startScan(
        timeout: const Duration(seconds: 7),
        continuousUpdates: true,
        continuousDivisor: divisor);
    setState(() {
      isScanning = true;
      scanResults.clear();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          SizedBox(height: 300,),
          // Flexible(
          //   flex: 1,
          //   child: ConnectedDevices(),
          // ),
          Flexible(
            flex: 4,
            child: ScanningDevices(),
          ),
        ],
      ),
    );
  }

  // Widget ConnectedDevices() {
  //   return Column(
  //     children: [
  //       Container(
  //         alignment: Alignment.centerLeft,
  //         child: const Text(
  //           'Connected Devices',
  //           style: TextStyle(
  //               color: blue400, fontSize: 18, fontWeight: FontWeight.w700),
  //         ),
  //       ),
  //       Expanded(
  //         child: ListView.builder(
  //           itemCount: connectedDevices.length,
  //           itemBuilder: (context, index) {
  //             var connectedDevice = connectedDevices[index];
  //             return Container(
  //               margin:
  //                   const EdgeInsets.symmetric(horizontal: 5.0, vertical: 5.0),
  //               height: 100,
  //               decoration: BoxDecoration(
  //                 borderRadius: BorderRadius.circular(20),
  //                 color: Colors.lightBlue[100],
  //               ),
  //               child: Row(
  //                 mainAxisAlignment: MainAxisAlignment.center,
  //                 children: [
  //                   const Icon(Icons.bluetooth),
  //                   const SizedBox(width: 8),
  //                   Column(
  //                     crossAxisAlignment: CrossAxisAlignment.center,
  //                     mainAxisAlignment: MainAxisAlignment.center,
  //                     children: [
  //                       Text(
  //                         connectedDevice as String,
  //                         style: const TextStyle(
  //                           fontSize: 14,
  //                           fontWeight: FontWeight.bold,
  //                         ),
  //                       ),
  //                       const SizedBox(height: 4),
  //                       Text(
  //                         'ID : [${connectedDevice!.id.toString()}]',
  //                         style: const TextStyle(
  //                           fontSize: 10,
  //                         ),
  //                       ),
  //                     ],
  //                   ),
  //                   const SizedBox(
  //                     width: 20,
  //                   ),
  //                   Column(
  //                     children: [
  //                       ElevatedButton(
  //                         style: ButtonStyle(
  //                           backgroundColor:
  //                               MaterialStateProperty.all(Colors.lightBlue[50]),
  //                         ),
  //                         onPressed: () {
  //                           // TODO: Connect to selected device
  //                           connectedDevice.disconnect();
  //                           setState(() {
  //                             connectedDevices.remove(connectedDevice);
  //                           });
  //                         },
  //                         child: const Text('disConnect'),
  //                       ),
  //                     ],
  //                   ),
  //                 ],
  //               ),
  //             );
  //           },
  //         ),
  //       ),
  //     ],
  //   );
  // }

  Widget ScanningDevices() {
    return Column(
      children: [
        Row(
          children: [
            ElevatedButton(
              onPressed: isScanning ? null : getScanForDevice,
              child: Text(isScanning ? 'Scanning...' : 'Scan'),
            ),
            ElevatedButton(
              onPressed: stopScan,
              child: Text('Stop!'),
            ),
          ],
        ),
        Container(
          alignment: Alignment.centerLeft,
          child: const Text(
            'Scanning Devices',
            style: TextStyle(
                color: blue400,
                fontSize: 18,
                fontWeight: FontWeight.w700),
          ),
        ),
        const SizedBox(height: 10),
        Expanded(
          child: RefreshIndicator(
            onRefresh: () => stopScan().then((_) => getScanForDevice()),
            child: ListView.builder(
              itemCount: scanResults.length,
              itemBuilder: (BuildContext context, int index) {
                var scanResult = scanResults[index];
                // if (scanResult != []) {
                  return Container(
                    margin: const EdgeInsets.symmetric(
                        horizontal: 5.0, vertical: 5.0),
                    height: 100,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(20),
                      color: Colors.grey[200],
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        const Icon(Icons.bluetooth),
                        const SizedBox(width: 8),
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.center,
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Text(
                              scanResult.advertisementData.advName,
                              style: const TextStyle(
                                fontSize: 14,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            const SizedBox(height: 4),
                            // Text(
                            //   'ID : [${scanResult.device.id.toString()}]',
                            //   style: const TextStyle(
                            //     fontSize: 10,
                            //   ),
                            // ),
                          ],
                        ),
                        const SizedBox(
                          width: 20,
                        ),
                        // ElevatedButton(
                        //   style: ButtonStyle(
                        //       backgroundColor: MaterialStateProperty.all(
                        //           Colors.lightBlue[50]),
                        //       foregroundColor:
                        //           MaterialStateProperty.all(Colors.black)),
                        //   onPressed: () {
                        //     // TODO: Connect to selected device
                        //     scanResult.device.connect();
                        //
                        //     setState(() {
                        //       scanResults.remove(scanResult.device);
                        //       connectedDevices.add(scanResult.device);
                        //     });
                        //   },
                        //   child: const Text('Connect'),
                        // ),
                      ],
                    ),
                  );
                // } else {
                //   return Container();
                // }
              },
            ),
          ),
        ),
      ],
    );
  }

  // void startScan() async {
  //   setState(() {
  //     isScanning = true;
  //     scanResults.clear();
  //   });
  //   try {
  //     FlutterBluePlus.scanResults.listen((scanResult) {
  //       setState(() {
  //         scanResults.add(scanResult as ScanResult);
  //       });
  //     });
  //   } catch (e) {
  //     debugPrint('error : ${e.toString()}');
  //   }
  // }

  Future<void> stopScan() async {
    setState(() {
      isScanning = false;
    });
    await FlutterBluePlus.stopScan();
  }
}
