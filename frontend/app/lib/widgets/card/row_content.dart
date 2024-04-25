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

    return Row(
      children: [
        Text(
          '$title: ', // 제목 뒤에 콜론 추가
          style: const TextStyle(
            color: samsungBlue, // samsungBlue 색상 코드
             // 제목을 굵게
          ),
        ),
        Expanded( // content를 Expanded로 감싸서 줄바꿈을 자동으로 처리
          child: Text(
            content,
            softWrap: true, // 줄바꿈 활성화
             // 넘치는 텍스트는 생략 처리
          ),
        ),
      ],
    );
  }
}