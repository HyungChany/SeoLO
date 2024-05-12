// import 'package:flutter/material.dart';
// import '../../main.dart';
//
// class CheckBoxList extends StatefulWidget {
//   final String title;
//   final Function(bool)? onChecked;
//
//   const CheckBoxList({super.key, required this.title, this.onChecked});
//
//   @override
//   State<CheckBoxList> createState() => _CheckBoxListState();
// }
//
// class _CheckBoxListState extends State<CheckBoxList> {
//   bool _isChecked = false;
//
//   @override
//   Widget build(BuildContext context) {
//     return Center(
//       child: CheckboxListTile(
//         title: Text(widget.title),
//         value: _isChecked,
//         onChanged: (value) {
//           setState(() {
//             (_isChecked == true) ? _isChecked = value!;
//           });
//           widget.onChecked?.call(_isChecked);
//         },
//         checkColor: snow,
//         activeColor: samsungBlue,
//       ),
//     );
//   }
// }