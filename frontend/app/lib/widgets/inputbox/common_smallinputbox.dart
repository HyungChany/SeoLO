import 'package:app/main.dart';
import 'package:flutter/material.dart';

class SmallInputBox extends StatelessWidget {
  final String hintText; // 외부에서 받아오는 hintText 값을 저장할 변수를 선언
  final TextInputAction textInputAction;
  // 생성자에서 hintText를 required 키워드와 함께 요구하여, 이 값을 반드시 제공해야 하도록 합니다.
  const SmallInputBox({super.key, required this.hintText, required this.textInputAction});

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: MediaQuery.of(context).size.width * 0.82,
      height: MediaQuery.of(context).size.height * 0.06,
      child: TextFormField(
        textAlignVertical: TextAlignVertical.center,
        textInputAction: textInputAction,
        validator: (value) {
          if (value!.isEmpty) {
            Text('내용을 입력해주세요');
          }
        },
        decoration: InputDecoration(
          filled: true,
          fillColor: Colors.white,
          border: const OutlineInputBorder(
              borderRadius: BorderRadius.all(Radius.circular(8.0))),
          hintText:
              hintText, // TextField의 hintText 속성에 생성자를 통해 받은 hintText를 사용
          hintStyle: TextStyle(color: gray200, fontSize: 18.0, fontWeight: FontWeight.w400)
        ),
      ),
    );
  }
}
