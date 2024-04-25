import 'package:flutter/material.dart';
import '../../main.dart';

class CheckBoxList extends StatefulWidget {

  final String title;

  const CheckBoxList({super.key,required this.title});
  @override
  State<CheckBoxList> createState() =>
      _CheckBoxListState();
}

class _CheckBoxListState extends State<CheckBoxList> {
  bool _isChecked = false;

  @override
  Widget build(BuildContext context) {
    return Center(
        child: CheckboxListTile(
          title: Text(widget.title),
          value: _isChecked,
          onChanged: (bool? newValue) {
            setState(() {
              _isChecked = newValue ?? false; // 체크박스 상태 업데이트
            });
          },
          checkColor: snow,
          activeColor: samsungBlue,
        ),

      );

  }
}