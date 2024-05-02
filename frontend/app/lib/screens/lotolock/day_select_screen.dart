import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:intl/intl.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/common_text_button.dart';

class DaySelect extends StatefulWidget {
  const DaySelect({super.key});

  @override
  _DaySelectState createState() => _DaySelectState();
}

class _DaySelectState extends State<DaySelect> {
  @override
  Widget build(BuildContext context) {
    final initDate = DateFormat('yyyy-MM-dd').parse('2024-04-26');
    return Scaffold(
      appBar: Header(title: '날짜 선택', back: true),
      body: Stack(
        children: [
          Center(
            child: Container(
              height: 300,
              child: CupertinoDatePicker(
                maximumYear: DateTime.now().year + 10,
                minimumYear: DateTime.now().year - 10,
                initialDateTime: initDate,
                mode: CupertinoDatePickerMode.date,
                onDateTimeChanged: (DateTime date) {
                  setState(() {
                    // firstDay = date;
                  });
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
                Navigator.pushNamed(context, '/timeselect');
              },
            ),
          ),
        ],
      ),
    );
  }
}
