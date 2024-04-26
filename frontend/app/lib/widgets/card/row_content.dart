import 'package:flutter/material.dart';
import '../../main.dart';
class RowContent extends StatelessWidget{
  final String title;
  final String content;

  const RowContent({
    required this.title,
    required this.content
});
  @override
  Widget build(BuildContext context){

    return Padding(
      padding: const EdgeInsets.only(left: 8.0),
      child: Row(
        children: [
          Container(
            width: 65,
            child: Text(
              '$title', // 제목 뒤에 콜론 추가
              style: const TextStyle(
                color: samsungBlue, // samsungBlue 색상 코드
                 // 제목을 굵게
              ),
            ),
          ),
          Expanded( // content를 Expanded로 감싸서 줄바꿈을 자동으로 처리
            child: Text(
              content,
              softWrap: true, // 줄바꿈 활성화
              overflow: TextOverflow.ellipsis,
              maxLines: 2,
            ),
          ),
        ],
      ),
    );
  }
}