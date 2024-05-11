import 'package:app/view_models/core/core_issue_view_model.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:intl/intl.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/button/common_text_button.dart';
import 'package:provider/provider.dart';

class DaySelect extends StatefulWidget {
  const DaySelect({super.key});

  @override
  State<DaySelect> createState() => _DaySelectState();
}

class _DaySelectState extends State<DaySelect> {
  String endDay = DateFormat('yyyy-MM-dd').format(DateTime.now());

  @override
  Widget build(BuildContext context) {
    final coreViewModel = Provider.of<CoreIssueViewModel>(context);
    final today = DateTime.now();
    final selectedDate = DateFormat('yyyy-MM-dd').parse(endDay);
    final initDate = DateFormat('yyyy-MM-dd')
        .parse('${today.year}-${today.month}-${today.day}');

    return Scaffold(
      appBar: const Header(title: '날짜 선택', back: true),
      body: Stack(
        children: [
          Center(
            child: SizedBox(
              height: 300,
              child: CupertinoDatePicker(
                maximumYear: DateTime.now().year + 10,
                minimumYear: DateTime.now().year,
                initialDateTime: initDate,
                mode: CupertinoDatePickerMode.date,
                onDateTimeChanged: (DateTime date) {
                  setState(() {
                    endDay = DateFormat('yyyy-MM-dd').format(date);
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
                  if (selectedDate.isBefore(today.subtract(const Duration(days: 1)))) {
                    showDialog(
                        context: context,
                        barrierDismissible: true,
                        builder: (BuildContext context) {
                          return const CommonDialog(
                            content: '오늘 이후의 날짜를 선택해 주세요.',
                            buttonText: '확인',
                          );
                        });
                  } else {
                    coreViewModel.setEndDay(endDay);
                    Navigator.pushNamed(context, '/selectTime');
                  }
                }),
          ),
        ],
      ),
    );
  }
}
