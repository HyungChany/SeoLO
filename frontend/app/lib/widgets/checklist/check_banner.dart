import 'package:flutter/material.dart';
import '../../main.dart';

class CheckBanner extends StatelessWidget {
  final String word;
  final String content;

  const CheckBanner({super.key,required this.word, required this.content});

  @override
  Widget build(BuildContext context) {
    return RichText(
        text:  TextSpan(
            style: TextStyle(
                fontSize: 16, fontWeight: FontWeight.bold, color: Colors.black),
            children: [
          TextSpan(text: word, style: TextStyle(color: red300)),
          TextSpan(text: content)
        ]));
  }
}
