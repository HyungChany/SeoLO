import 'package:app/main.dart';
import 'package:flutter/material.dart';

class LotoDefinition extends StatelessWidget {
  const LotoDefinition({super.key});

  content() {
    return Center(
      child: Padding(
        padding: const EdgeInsets.only(left: 20.0, right: 20.0),
        child: RichText(
            text: const TextSpan(
                style: TextStyle(
                    fontFamily: 'font',
                    fontSize: 21,
                    fontWeight: FontWeight.w400,
                    color: Colors.black,
                    height: 1.5),
                children: [
              TextSpan(text: 'LOTO', style: TextStyle(color: safetyRed)),
              TextSpan(
                  text:
                      '은 잠금장치, 표지판의 줄임말로, 사고를 유발할 수 있는 장비의 수리나 정비, 또는 청소 등을 수행하기 위해 기동장치를  '),
              TextSpan(
                  text: '잠그거나, 표지판을 설치', style: TextStyle(color: safetyRed)),
              TextSpan(text: '하는 등의 조치를 말한다.')
            ])),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      height: MediaQuery.of(context).size.height * 0.25,
      child: content(),
      decoration: BoxDecoration(
          color: yellow200, borderRadius: BorderRadius.circular(10.0)),
    );
  }
}
