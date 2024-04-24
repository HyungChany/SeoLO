import 'package:flutter/material.dart';
import '../main.dart';

class CommonTextButton extends StatelessWidget {
  final String text; // 버튼 내용
  final double? width; // 너비
  final double? height; // 높이
  final VoidCallback onTap; // 클릭 이벤트

  const CommonTextButton({
    super.key,
    required this.text,
    this.width = 300.0,
    this.height = 45.0,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        width: width,
        height: height,
        decoration: BoxDecoration(
          color: blue100,
          borderRadius: BorderRadius.circular(10.0),
        ),
        alignment: Alignment.center,
        child: Text(
          text,
          textAlign: TextAlign.center,
          style: const TextStyle(
              fontSize: 16.0, fontWeight: FontWeight.w500, color: Colors.white),
        ),
      ),
    );
  }
}
