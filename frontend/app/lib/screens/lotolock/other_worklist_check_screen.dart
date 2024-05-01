import 'package:flutter/material.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/common_text_button.dart';
import 'package:app/main.dart';

class OtherWorkListCheckScreen extends StatefulWidget {
  const OtherWorkListCheckScreen({super.key});

  @override
  _OtherWorkListCheckScreenState createState() => _OtherWorkListCheckScreenState();
}

class _OtherWorkListCheckScreenState extends State<OtherWorkListCheckScreen> {
  List<String> workListTitle = [
    '작업장',
    '장비 명',
    '장비 번호',
    '작업 담당자',
    '시작 시간',
    '종료 시간',
    '작업 내용'
  ];
  List<String> workListContent = [
    '1공장 검사 라인',
    'L/W - 2',
    '3578DA1204',
    '생산기술팀 오정민 팀장',
    '2024.05.01 12:00',
    '2024.05.01 14:00',
    '수리'
  ];

  String remark = "작업이 완료되었습니다. 추가 점검이 필요합니다.";

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Header(title: '작업 내역', back: true),
      body: Stack(
        children: [
          SingleChildScrollView(
            child: Padding(
              padding: EdgeInsets.symmetric(horizontal: 16.0), // 전체 내용의 양쪽 패딩
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  ListView.builder(
                    shrinkWrap: true,
                    physics: NeverScrollableScrollPhysics(),
                    itemCount: workListTitle.length,
                    itemBuilder: (context, index) {
                      return ListTile(
                        title: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: <Widget>[
                            Text(workListTitle[index], style: TextStyle(color: samsungBlue)),
                            Expanded(
                              child: Text(
                                workListContent[index],
                                textAlign: TextAlign.right,
                              ),
                            ),
                          ],
                        ),
                      );
                    },
                  ),
                  Padding(
                    padding: EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0), // 비고 라벨의 패딩
                    child: Text('비고', style: TextStyle(fontSize: 16.0, color: samsungBlue)),
                  ),
                  Container(
                    height: 260,
                    padding: EdgeInsets.all(16.0),
                    margin: EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0), // 비고 컨테이너의 마진
                    decoration: BoxDecoration(
                      color: Colors.grey[200],
                      border: Border.all(color: Colors.grey),
                      borderRadius: BorderRadius.circular(8.0),
                    ),
                    child: SingleChildScrollView(
                      child: Text(remark, style: TextStyle(fontSize: 16.0)),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Align(
            alignment: Alignment.bottomCenter,
            child: Padding(
              padding: EdgeInsets.only(bottom: 20, left: 50, right: 50),
              child: CommonTextButton(
                text: '확인',
                onTap: () {
                  Navigator.pushNamed(context, '/main');
                },
              ),
            ),
          ),
        ],
      ),
    );
  }
}
