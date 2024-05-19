import 'package:app/view_models/core/core_check_view_model.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/button/common_text_button.dart';
import 'package:app/main.dart';
import 'package:provider/provider.dart';

class OtherWorkListCheckScreen extends StatefulWidget {
  const OtherWorkListCheckScreen({super.key});

  @override
  State<OtherWorkListCheckScreen> createState() =>
      _OtherWorkListCheckScreenState();
}

class _OtherWorkListCheckScreenState extends State<OtherWorkListCheckScreen> {
  List<String> workListTitle = [
    '작업장',
    '장비 명',
    '장비 번호',
    '작업 담당자',
    '시작 시간',
    '종료 예상 시간',
    '작업 내용'
  ];

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<CoreCheckViewModel>(context);
    String? startTimeString = viewModel.startTime ?? '';
    List<String> startParts = startTimeString.split('T');
    String formattedStartTime = (startTimeString == '')
        ? ''
        : '${startParts[0]} | ${startParts[1].substring(0, 5)}';

    String? endTimeString = viewModel.endTime ?? '';
    List<String> endParts = endTimeString.split('T');
    String formattedEndTime = (endTimeString == '')
        ? ''
        : '${endParts[0]} | ${endParts[1].substring(0, 5)}';

    final List<String> workListContent = [
      viewModel.facilityName ?? '조회 불가',
      viewModel.machineName ?? '조회 불가',
      viewModel.machineCode ?? '조회 불가',
      '${viewModel.workerTeam} ${viewModel.workerName} ${viewModel.workerTitle}',
      formattedStartTime,
      formattedEndTime,
      viewModel.taskType ?? '조회 불가',
    ];
    return Scaffold(
      appBar: Header(title: '${viewModel.workerName}님의 작업 내역', back: true),
      body: Stack(
        children: [
          SingleChildScrollView(
            child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              // 전체 내용의 양쪽 패딩
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  ListView.builder(
                    shrinkWrap: true,
                    physics: const NeverScrollableScrollPhysics(),
                    itemCount: workListTitle.length,
                    itemBuilder: (context, index) {
                      return ListTile(
                        title: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: <Widget>[
                            Text(workListTitle[index],
                                style: const TextStyle(color: samsungBlue)),
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
                  const Padding(
                    padding: EdgeInsets.symmetric(
                        vertical: 8.0, horizontal: 16.0), // 비고 라벨의 패딩
                    child: Text('비고',
                        style: TextStyle(fontSize: 16.0, color: samsungBlue)),
                  ),
                  Container(
                    width: MediaQuery.of(context).size.width * 0.9,
                    height: MediaQuery.of(context).size.height * 0.25,
                    padding: const EdgeInsets.all(16.0),
                    margin: const EdgeInsets.symmetric(
                        horizontal: 16.0, vertical: 8.0),
                    decoration: BoxDecoration(
                      color: Colors.grey[200],
                      border: Border.all(color: Colors.grey),
                      borderRadius: BorderRadius.circular(8.0),
                    ),
                    child: SingleChildScrollView(
                      child: Text(viewModel.precaution ?? '조회 불가',
                          style: TextStyle(fontSize: 16.0)),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Align(
            alignment: Alignment.bottomCenter,
            child: Padding(
              padding: const EdgeInsets.only(bottom: 20, left: 50, right: 50),
              child: CommonTextButton(
                text: '확인',
                onTap: () {
                  Navigator.pushNamedAndRemoveUntil(
                      context, '/main', (route) => false);
                },
              ),
            ),
          ),
        ],
      ),
    );
  }
}
