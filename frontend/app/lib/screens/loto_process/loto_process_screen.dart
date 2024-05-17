import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/loto_process/loto_definition.dart';
import 'package:app/widgets/loto_process/loto_work_process.dart';
import 'package:flutter/material.dart';

class LotoProcessScreen extends StatelessWidget {
  const LotoProcessScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const Header(
        title: 'LOTO 정의',
        back: true,
      ),
      body: Center(
        child: SizedBox(
          width: MediaQuery.of(context).size.width * 0.9,
          child: SingleChildScrollView(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text('LOTO란?', style: TextStyle(fontSize: 30),),
                LotoDefinition(),
                const Text('LOTO 작업절차', style: TextStyle(fontSize: 30)),
                LotoWorkProcess(),
                Image.asset('assets/images/loto_process_character.png',fit: BoxFit.contain,)
              ],
            ),
          ),
        ),
      ),
    );
  }
}
