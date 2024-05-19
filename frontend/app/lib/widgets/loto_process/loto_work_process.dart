import 'package:app/main.dart';
import 'package:flutter/material.dart';

class LotoWorkProcess extends StatelessWidget {
  LotoWorkProcess({super.key});

  List<String> content = [
    '전원차단 준비',
    '기계설비 운전 정지',
    '전원차단 및 잔류에너지 확인',
    'LOTO 설치',
    '작업실시',
    '점검 및 확인',
    'LOTO 해제',
    '기계 설비 재가동'
  ];

  container(text) {
    return Column(
      children: [
        Container(
          height: 55,
          decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(20), color: blue400),
          child: Center(
            child: Text(
              text,
              style:
                  TextStyle(color: snow, fontSize: 25, fontWeight: FontWeight.bold),
            ),
          ),
        ),
        SizedBox(height: 10,)
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: MediaQuery.of(context).size.width * 0.9,
      height: MediaQuery.of(context).size.height * 0.6,
      child: ListView.builder(
        // scrollDirection: Axis.vertical,
        itemCount: content.length,
        itemBuilder: (BuildContext context, int index) {
          return container(content[index]);
        },
      ),
    );
  }
}
