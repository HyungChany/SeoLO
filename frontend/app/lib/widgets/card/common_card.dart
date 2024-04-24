import 'package:flutter/material.dart';

class CommonCard extends StatefulWidget {

  const CommonCard({super.key});

  @override
  _CommonCardState createState() => _CommonCardState();
}
class _CommonCardState extends State<CommonCard> {
  @override
  Widget build(BuildContext context) {
    final screenWidth = MediaQuery.of(context).size.width;
    final screenHeight = MediaQuery.of(context).size.height;

    // 화면 크기에 따라 width와 height를 설정합니다.
    // 예를 들어, 화면 너비의 30%와 높이의 20%를 사용합니다.
    double width = screenWidth * 0.3;
    double height = screenHeight * 0.2;
    return Container(
      width: width,
      height: height,
      decoration: BoxDecoration(
        color: Color.fromRGBO(237, 244, 251, 1), // 컨테이너 배경색
        borderRadius: BorderRadius.circular(10.0), // 모서리 둥글기
        boxShadow: const [
          BoxShadow(
            color: Color.fromRGBO(0, 0, 0, 0.25), // 그림자 확산 범위
            blurRadius: 5, // 그림자 흐림 정도
            offset: Offset(0, 2), // 그림자 위치
          ),
        ],
      ),
      child: const Column(

      ),
      // 컨테이너 내부의 자식 위젯들을 추가합니다.
    );
  }
}