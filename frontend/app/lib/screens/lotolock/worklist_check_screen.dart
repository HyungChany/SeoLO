import 'package:app/view_models/core/core_issue_view_model.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/button/common_text_button.dart';
import 'package:app/main.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class WorkListCheckScreen extends StatefulWidget {
  const WorkListCheckScreen({super.key});

  @override
  State<WorkListCheckScreen> createState() => _WorkListCheckScreenState();
}

class _WorkListCheckScreenState extends State<WorkListCheckScreen> {
  final _storage = const FlutterSecureStorage();
  String? lockerUid;
  List<String> workListTitle = ['작업장', '장비 명', '작업 담당자', '종료 예상 시간', '작업 내용'];

  @override
  void initState() {
    super.initState();
    isConnect();
  }

  void isConnect() async {
    lockerUid = await _storage.read(key: 'locker_uid');
    debugPrint(lockerUid);
  }

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
                    width: screenWidth * 0.9,
                    height: screenHeight * 0.3,
                    padding: const EdgeInsets.all(16.0),
                    margin: const EdgeInsets.symmetric(
                        horizontal: 16.0, vertical: 8.0),
                    decoration: BoxDecoration(
                      color: Colors.grey[200],
                      border: Border.all(color: Colors.grey),
                      borderRadius: BorderRadius.circular(8.0),
                    ),
                    child: SingleChildScrollView(
                      child: Text(viewModel.taskPrecaution!,
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
                text: '작업 내역 저장 및 자물쇠 잠금',
                onTap: () async {
                  // machine id 저장
                  await _storage.write(
                      key: 'machine_id', value: viewModel.machineId.toString());
                  // storage에 locker uid가 있다면 연결 먼저 한 것
                  // 그럼 issue api 요청 후 bluetooth 이동
                  if (lockerUid != null) {
                    if (!viewModel.isLoading) {
                      viewModel.coreIssue().then((_) {
                        // 일지 작성 후 lock 하러 가기!
                        if (viewModel.errorMessage == null) {
                          Navigator.pushNamedAndRemoveUntil(
                            context,
                            '/bluetooth',
                                (Route<dynamic> route) => route.isFirst,
                          );
                          // Navigator.pushNamedAndRemoveUntil(context,
                          //     '/bluetooth', ModalRoute.withName('/main'));
                        } else {
                          if (viewModel.errorMessage == 'JT') {
                            showDialog(
                                context: context,
                                barrierDismissible: false,
                                builder: (BuildContext context) {
                                  return CommonDialog(
                                    content: '토큰이 만료되었습니다. 다시 로그인 해주세요.',
                                    buttonText: '확인',
                                    buttonClick: () {
                                      Navigator.pushNamedAndRemoveUntil(
                                          context, '/login', (route) => false);
                                    },
                                  );
                                });
                          } else {
                            showDialog(
                                context: context,
                                barrierDismissible: false,
                                builder: (BuildContext context) {
                                  return CommonDialog(
                                    content: viewModel.errorMessage!,
                                    buttonText: '확인',
                                    buttonClick: () {
                                      Navigator.pushNamedAndRemoveUntil(
                                          context, '/main', (route) => false);
                                    },
                                  );
                                });
                          }
                        }
                      });
                    }
                  } else {
                    // locker uid가 null이라면 일지 먼저 작성한 것
                    // Navigator.pushNamedAndRemoveUntil(
                    //   context,
                    //   '/bluetooth',
                    //       (Route<dynamic> route) => route.isFirst,
                    // );
                    Navigator.pushNamedAndRemoveUntil(
                        context, '/bluetooth', ModalRoute.withName('/main'));
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
