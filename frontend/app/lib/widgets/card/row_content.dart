import 'package:flutter/material.dart';
import '../../main.dart';

class RowContent extends StatelessWidget {
  final String title;
  final String content;
  final int line;

  const RowContent({
    super.key,
    required this.title,
    required this.content,
    required this.line,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(left: 8.0),
      child: Row(
        children: [
          SizedBox(
            width: 65,
            child: Text(
              title, // 제목 뒤에 콜론 추가
              style: const TextStyle(
                color: samsungBlue,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
          Expanded(
            child: Text(
              content,
              softWrap: true, // 줄바꿈 활성화
              overflow: TextOverflow.ellipsis,
              maxLines: line,
            ),
          ),
        ],
      ),
    );
  }
}
