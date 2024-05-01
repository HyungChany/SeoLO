import 'package:flutter/material.dart';

class SelectList extends StatefulWidget {
  final String title;

  const SelectList({super.key, required this.title});
  @override
  State<SelectList> createState() =>
      _SelectListState();
}

class _SelectListState extends State<SelectList>{
  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () {
        Navigator.pushNamed(context, '/worklist');
      },
      child: Container(
        height: 60,
        padding: EdgeInsets.only(left: 30), // 왼쪽에서 16픽셀 떨어진 곳에 Text를 배치
        alignment: Alignment.centerLeft, // 세로 기준 가운데, 왼쪽 정렬
        child: Text(widget.title,
          style: const TextStyle(
            fontWeight: FontWeight.bold, // 폰트 굵게
            fontSize: 16, // 폰트 사이즈 설정, 필요에 따라 조절
          ),),

      ),
    );
  }
}