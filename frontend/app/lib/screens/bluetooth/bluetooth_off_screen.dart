import 'dart:io';

import 'package:app/main.dart';
import 'package:app/widgets/button/common_text_button.dart';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

class BluetoothOffScreen extends StatelessWidget {
  const BluetoothOffScreen({super.key, this.adapterState});

  final BluetoothAdapterState? adapterState;

  Widget buildBluetoothOffIcon(BuildContext context) {
    return const Icon(
      Icons.bluetooth_disabled,
      size: 200.0,
      color: blue100,
    );
  }

  Widget buildTurnOnButton(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: CommonTextButton(text: '블루투스 켜기', onTap: () async {
        try {
          if (Platform.isAndroid) {
            await FlutterBluePlus.turnOn();
            Navigator.pushReplacementNamed(context, '/bluetooth');
          }
        } catch (e) {
          debugPrint("Error Turning On: $e");
        }
      },),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            buildBluetoothOffIcon(context),
            if (Platform.isAndroid) buildTurnOnButton(context),
          ],
        ),
      ),
    );
  }
}
