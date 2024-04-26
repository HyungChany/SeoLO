import 'package:flutter/material.dart';
import 'package:app/widgets/card/row_content.dart';
class CommonCard extends StatefulWidget {

  const CommonCard({super.key});

  @override
  _CommonCardState createState() => _CommonCardState();
}
class _CommonCardState extends State<CommonCard> {
  final List<Map<String, String>> data = [
    {'title': '작업장', 'content': '1공장 검사라인'},
    {'title': '설비명', 'content': 'L / W - 2'},
    {'title': '시작시간', 'content': 'MM.DD. hh.mm'},
    {'title': '종료시간', 'content': 'MM.DD. hh.mm'},
  ];
  @override
  Widget build(BuildContext context) {
    final screenWidth = MediaQuery.of(context).size.width;
    final screenHeight = MediaQuery.of(context).size.height;

    // 화면 크기에 따라 width와 height를 설정합니다.
    // 예를 들어, 화면 너비의 30%와 높이의 20%를 사용합니다.
    double width = screenWidth * 0.35;
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
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly, // 위젯 사이에 고르게 간격 추가
        children: data.map((item) {
          return Padding(
            padding: const EdgeInsets.symmetric(vertical: 4.0), // 각 RowContent 위젯 사이에 패딩 추가
            child: RowContent(title: item['title']!, content: item['content']!),
          );
        }).toList(),
      ),
      // 컨테이너 내부의 자식 위젯들을 추가합니다.
    );
  }
}