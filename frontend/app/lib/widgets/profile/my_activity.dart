import 'package:app/main.dart';
import 'package:app/view_models/user/my_info_view_model.dart';
import 'package:app/widgets/profile/icon_with_text.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class MyActivity extends StatefulWidget {
  const MyActivity({super.key});

  @override
  _MyActivityState createState() => _MyActivityState();
}

class _MyActivityState extends State<MyActivity> {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        IconWithText(
          icon: Icon(
            Icons.edit_document,
            size: 35,
          ),
          text: '비밀번호 재설정',
          naviPage: '/checkPassword',
        ),
        IconWithText(
          icon: Icon(
            Icons.password,
            size: 35,
          ),
          text: 'PIN 번호 재설정',
          naviPage: '/checkPin',
        )
      ],
    );
  }
}
