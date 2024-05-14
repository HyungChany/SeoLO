import 'package:app/view_models/core/core_issue_view_model.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/button/common_text_button.dart';
import 'package:app/main.dart';
import 'package:provider/provider.dart';

class WorkListCheckScreen extends StatefulWidget {
  const WorkListCheckScreen({super.key});

  @override
  State<WorkListCheckScreen> createState() => _WorkListCheckScreenState();
}

class _WorkListCheckScreenState extends State<WorkListCheckScreen> {
  List<String> workListTitle = [
    '작업장',
    '장비 명',
    '작업 담당자',
    '종료 예상 시간',
    '작업 내용'
  ];


  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<CoreIssueViewModel>(context);
    final List<String> workListContent = [
      viewModel.facilityName!,
      viewModel.machineName!,
      viewModel.manager!,
      viewModel.endTime!,
      viewModel.taskTemplateName!,
    ];
    double screenWidth = MediaQuery.of(context).size.width; // 화면 너비
    double screenHeight = MediaQuery.of(context).size.height; // 화면 높이
    return Scaffold(
      appBar: const Header(title: '작업 내역 확인', back: true),
      body: Stack(
        children: [
          SingleChildScrollView(
            child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0), // 전체 내용의 양쪽 패딩
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
                    width: screenWidth * 0.9,
                    height: screenHeight * 0.3,
                    padding: const EdgeInsets.all(16.0),
                    margin:
                        const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
                    decoration: BoxDecoration(
                      color: Colors.grey[200],
                      border: Border.all(color: Colors.grey),
                      borderRadius: BorderRadius.circular(8.0),
                    ),
                    child: SingleChildScrollView(
                      child: Text(viewModel.taskPrecaution!, style: TextStyle(fontSize: 16.0)),
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
                  if (!viewModel.isLoading) {
                    viewModel.coreIssue().then((_) {
                      // 일지 작성 후 lock 하러 가기!
                      if (viewModel.errorMessage == null) {
                        Navigator.pushNamedAndRemoveUntil(context, '/bluetooth', (route) => false);
                      } else {
                        showDialog(
                            context: context,
                            barrierDismissible: true,
                            builder: (BuildContext context) {
                              return CommonDialog(
                                content: viewModel.errorMessage!,
                                buttonText: '확인',
                              );
                            });
                      }
                    });
                  }
                },
              ),
            ),
          ),
        ],
      ),
    );
  }
}
