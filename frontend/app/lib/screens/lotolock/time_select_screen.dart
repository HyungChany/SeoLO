import 'package:app/view_models/core/core_issue_view_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/button/common_text_button.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

class TimeSelect extends StatefulWidget {
  const TimeSelect({super.key});

  @override
  _TimeSelectState createState() => _TimeSelectState();
}

class _TimeSelectState extends State<TimeSelect> {
  String endTime = DateFormat('hh:mm').format(DateTime.now());
  @override
  Widget build(BuildContext context) {
    final coreViewModel = Provider.of<CoreIssueViewModel>(context);
    final initTime = DateFormat('hh:mm').parse(
        '${DateTime.now().hour}:${DateTime.now().minute}');
    return Scaffold(
      appBar: Header(title: '시간 선택', back: true),
      body: Stack(
        children: [
          Center(
            child: Container(
              height: 300,
              child: CupertinoDatePicker(
                mode: CupertinoDatePickerMode.time,
                initialDateTime: initTime,
                onDateTimeChanged: (DateTime date) {
                  setState(() {
                    endTime = DateFormat('hh:mm').format(date);
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
                coreViewModel.setEndTime(endTime);
                Navigator.pushNamed(context, '/worklistcheck');
              },
            ),
          ),
        ],
      ),
    );
  }
}
