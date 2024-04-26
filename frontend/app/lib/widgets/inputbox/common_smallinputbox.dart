import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class MyCustomForm extends StatelessWidget {
  final String hintText;  // 외부에서 받아오는 hintText 값을 저장할 변수를 선언

  // 생성자에서 hintText를 required 키워드와 함께 요구하여, 이 값을 반드시 제공해야 하도록 합니다.
  const MyCustomForm({super.key, required this.hintText});

  @override
  Widget build(BuildContext context) {
    double baseSize = Theme.of(context).textTheme.bodyMedium?.fontSize ?? 16.0; // 기본 글꼴 크기를 가져옴
    double width = baseSize * 20; // width: 17.5625rem;
    double height = baseSize * 2.5; // height: 2.5rem;

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        SizedBox(
          width: width,
          height: height,
          child: TextFormField(
              textAlignVertical: TextAlignVertical.bottom,
              decoration: InputDecoration(
                border: const OutlineInputBorder(),
                hintText: hintText,  // TextField의 hintText 속성에 생성자를 통해 받은 hintText를 사용
              )
          ),
        ),
      ],
    );
  }
}
