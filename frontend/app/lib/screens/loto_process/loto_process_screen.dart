import 'package:app/main.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/loto_process/loto_definition.dart';
import 'package:flutter/material.dart';

class LotoProcessScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Header(
        title: 'LOTO 정의',
        back: true,
      ),
      body: Center(
        child: Container(
          width: MediaQuery.of(context).size.width * 0.9,
          child: SingleChildScrollView(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text('LOTO란?', style: TextStyle(fontSize: 30),),
                LotoDefinition(),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
