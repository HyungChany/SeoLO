import 'package:app/main.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/card/row_content.dart';

class CommonCard extends StatefulWidget {
  final String facility;
  final String machine;
  final String start;
  final String end;

  const CommonCard(
      {super.key,
      required this.facility,
      required this.machine,
      required this.start,
      required this.end});

  @override
  _CommonCardState createState() => _CommonCardState();
}

class _CommonCardState extends State<CommonCard> {
  late List<Map<String, String>> data = [
    {'title': '작업장', 'content': widget.facility},
    {'title': '설비명', 'content': widget.machine},
    {'title': '시작시간', 'content': widget.start},
    {'title': '종료시간', 'content': widget.end},
  ];

  @override
  Widget build(BuildContext context) {
    final screenWidth = MediaQuery.of(context).size.width;
    final screenHeight = MediaQuery.of(context).size.height;

    double width = screenWidth * 0.35;
    double height = screenHeight * 0.23;
    return Container(
      width: width,
      height: height,
      decoration: BoxDecoration(
        color: Color.fromRGBO(237, 244, 251, 1), // 컨테이너 배경색
        borderRadius: BorderRadius.circular(10.0), // 모서리 둥글기
        boxShadow: const [shadow],
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly, // 위젯 사이에 고르게 간격 추가
        children: data.map((item) {
          return Padding(
            padding: const EdgeInsets.symmetric(vertical: 4.0),
            // 각 RowContent 위젯 사이에 패딩 추가
            child: RowContent(title: item['title']!, content: item['content']!),
          );
        }).toList(),
      ),
      // 컨테이너 내부의 자식 위젯들을 추가합니다.
    );
  }
}
