import 'package:flutter/material.dart';
import '../../main.dart';

class CheckBanner extends StatelessWidget{
  const CheckBanner({super.key});

  @override
  Widget build(BuildContext context){
    return RichText(text : const TextSpan(
        style: TextStyle(
            fontSize: 16, fontWeight: FontWeight.bold, color: Colors.black),
        children: [
          TextSpan(text: '체크리스트', style: TextStyle(color: red300)),
          TextSpan(text: '의 모든 항목을 확인해주세요 ')
        ]));
  }
}