import 'package:app/widgets/common_text_button.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/inputbox/common_smallinputbox.dart';
import 'package:flutter/material.dart';

class ChangePassword extends StatefulWidget {
  @override
  _ChangePasswordState createState() => _ChangePasswordState();
}

class _ChangePasswordState extends State<ChangePassword> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Header(title: '비밀번호 재설정', back: true),
      body: Column(
        children: [
          // SmallInputBox(hintText: '현재 비밀번호', textInputAction: TextInputAction.next,),
          // SmallInputBox(hintText: '새 비밀번호', textInputAction: TextInputAction.next,),
          // SmallInputBox(hintText: '새 비밀번호 확인', textInputAction: TextInputAction.done,),
          CommonTextButton(text: '확인', onTap: () {})
        ],
      ),
    );
  }
}