import 'package:flutter/material.dart';
import '../../main.dart';

class CommonCard extends StatefulWidget {
  final double width; // 너비
  final double height; // 높이

  const CommonCard({
    super.key,
    this.width = 300.0,
    this.height = 45.0,
  });

  @override
  _CommonCardState createState() => _CommonCardState();
}
class _CommonCardState extends State<CommonCard> {
  @override
  Widget build(BuildContext context) {
    return Container(
      width: widget.width,
      height: widget.height,
      decoration: BoxDecoration(
        color: Color.fromRGBO(135, 185, 231, 0.5), // 컨테이너 배경색
        borderRadius: BorderRadius.circular(10.0), // 모서리 둥글기
        boxShadow: [
          shadow
        ],
      ),
      child: const Column(

      ),
      // 컨테이너 내부의 자식 위젯들을 추가합니다.
    );
  }
}