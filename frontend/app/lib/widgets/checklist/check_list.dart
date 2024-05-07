import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../main.dart';

class CheckBoxList extends StatefulWidget {
  final String title;
  final Function(bool)? onChecked;

  const CheckBoxList({Key? key, required this.title, this.onChecked}) : super(key: key);

  @override
  _CheckBoxListState createState() => _CheckBoxListState();
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
            _isChecked = newValue ?? false;
          });
          widget.onChecked?.call(_isChecked); 
        },
        checkColor: snow,
        activeColor: samsungBlue,
      ),
    );
  }
}