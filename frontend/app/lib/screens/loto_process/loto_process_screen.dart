import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/loto_process/loto_definition.dart';
import 'package:flutter/material.dart';

class LotoProcessScreen extends StatefulWidget {
  const LotoProcessScreen({super.key});

  @override
  State<LotoProcessScreen> createState() => _LotoProcessState();
}

class _LotoProcessState extends State<LotoProcessScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const Header(
        title: 'LOTO 정의',
        back: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(15.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'LOTO란?',
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
            LotoDefinition(),
            const Text('LOTO 작업절차',
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
            SingleChildScrollView(
              child: Image.asset(
                'assets/images/loto_process_character.png',
              ),
            )
          ],
        ),
      ),
    );
  }
}
