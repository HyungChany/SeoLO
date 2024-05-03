import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/button/common_text_button.dart';

class TimeSelect extends StatefulWidget {
  const TimeSelect({super.key});

  @override
  _TimeSelectState createState() => _TimeSelectState();
}

class _TimeSelectState extends State<TimeSelect> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Header(title: '시간 선택', back: true),
      body: Stack(
        children: [
          Center(
            child: Container(
              height: 300,
              child: CupertinoDatePicker(
                mode: CupertinoDatePickerMode.time,
                onDateTimeChanged: (DateTime date) {
                  setState(() {});
                },
              ),
            ),
          ),
          Positioned(
            bottom: 20,
            left: 50,
            right: 50,
            child: CommonTextButton(
              text: '다음 단계',
              onTap: () {
                Navigator.pushNamed(context, '/worklistcheck');
              },
            ),
          ),
        ],
      ),
    );
  }
}
