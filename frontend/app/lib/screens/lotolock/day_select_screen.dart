import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:intl/intl.dart';

class DaySelect extends StatefulWidget {
  // final void Function(DateTime) onDateTimeChanged;
  // final String initDateStr;

  // @override
  // const DaySelect({super.key,
  //   required this.onDateTimeChanged,
  //   required this.initDateStr,
  // });
  _DaySelectState createState() => _DaySelectState();
}
class _DaySelectState extends State<DaySelect>{

  @override
  Widget build(BuildContext context) {
    final initDate =
    DateFormat('yyyy-MM-dd').parse('2024-04-26');
    return Align(
      alignment: Alignment.center,
      child: Container(
        color: Colors.white,
        height: 300,
        child: CupertinoDatePicker(
          maximumYear: DateTime.now().year+10,
          minimumYear: DateTime.now().year,
          initialDateTime: initDate,

          mode: CupertinoDatePickerMode.date,
          onDateTimeChanged: (DateTime date){
            setState(() {
              // firstDay = date;
            });
          },

        ),
      ),
    );
  }
}
