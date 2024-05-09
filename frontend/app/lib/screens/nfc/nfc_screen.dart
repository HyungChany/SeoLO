import 'dart:typed_data';

import 'package:app/main.dart';
import 'package:flutter/material.dart';
import 'package:nfc_manager/nfc_manager.dart';

class NfcScreen extends StatefulWidget {
  @override
  _NfcScreenState createState() => _NfcScreenState();
}

class _NfcScreenState extends State<NfcScreen> {
  ValueNotifier<dynamic> result = ValueNotifier(null);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // backgroundColor: gray800,
      body: SafeArea(
        child: FutureBuilder<bool>(
          future: NfcManager.instance.isAvailable(),
          builder: (context, ss) => ss.data != true
              ? Center(child: Text('NfcManager.isAvailable(): ${ss.data}'))
              : Flex(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  direction: Axis.vertical,
                  children: [
                    Flexible(
                      flex: 2,
                      child: Container(
                        margin: EdgeInsets.all(4),
                        constraints: BoxConstraints.expand(),
                        decoration: BoxDecoration(border: Border.all()),
                        child: SingleChildScrollView(
                          child: ValueListenableBuilder<dynamic>(
                            valueListenable: result,
                            builder: (context, value, _) =>
                                Text('${value ?? ''}'),
                          ),
                        ),
                      ),
                    ),
                    Flexible(
                      flex: 3,
                      child: GridView.count(
                        padding: EdgeInsets.all(4),
                        crossAxisCount: 2,
                        childAspectRatio: 4,
                        crossAxisSpacing: 4,
                        mainAxisSpacing: 4,
                        children: [
                          ElevatedButton(
                              child: Text('Tag Read'), onPressed: _tagRead),
                          ElevatedButton(
                              child: Text('Ndef Write'), onPressed: _ndefWrite),
                        ],
                      ),
                    ),
                  ],
                ),
        ),
      ),
    );
  }

  void _tagRead() {
    NfcManager.instance.startSession(onError: (NfcError error) {
      result.value = 'Tag 인식 실패';
      return NfcManager.instance.stopSession();
    }, onDiscovered: (NfcTag tag) async {
      result.value = tag.data;
      debugPrint('NFC Tag Detected: ${tag.data}');
      // Navigator.pushNamed(context, '/main');
      NfcManager.instance.stopSession();
    });
  }

  void _ndefWrite() {
    NfcManager.instance.startSession(onError: (NfcError error) {
      result.value = 'Tag 인식 실패';
      return NfcManager.instance.stopSession();
    }, onDiscovered: (NfcTag tag) async {


      // var ndef = Ndef.from(tag);
      // result.value = tag.data;
      // if (ndef == null || !ndef.isWritable) {
      //   result.value = 'Tag is not ndef writable';
      //   NfcManager.instance.stopSession(errorMessage: result.value);
      //   return;
      // }

      NdefMessage message = NdefMessage([
        NdefRecord.createText('Hello World!'),
      ]);

      // try {
      //   await ndef?.write(message);
      //   result.value = 'Success to "Ndef Write"';
      //   NfcManager.instance.stopSession();
      // } catch (e) {
      //   result.value = e;
      //   NfcManager.instance.stopSession(errorMessage: result.value.toString());
      //   return;
      // }
    });

  }
}
