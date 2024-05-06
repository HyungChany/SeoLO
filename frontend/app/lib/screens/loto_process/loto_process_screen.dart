import 'package:app/widgets/header/header.dart';
import 'package:flutter/material.dart';

class LotoProcessScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Header(
        title: 'LOTO 정의',
        back: true,
      ),
      body: Text('LOTO란?'),
    );
  }
}
