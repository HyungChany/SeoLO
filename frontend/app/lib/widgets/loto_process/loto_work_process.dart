import 'package:app/main.dart';
import 'package:flutter/material.dart';

class LotoWorkProcess extends StatelessWidget {
  const LotoWorkProcess({super.key});

  // List<String> = ['전원차단 준비', '기계설비 '];
  container(text) {
    return Container(
      child: Text(
        text,
        style:
            TextStyle(color: snow, fontSize: 30, fontWeight: FontWeight.bold),
        textAlign: TextAlign.center,
      ),
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(10), color: blue100),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: MediaQuery.of(context).size.width * 0.9,
      height: MediaQuery.of(context).size.height * 0.06,
      child: container('전원차단 준비'),
    );
  }
}
