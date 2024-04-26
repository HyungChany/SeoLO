import 'package:app/widgets/common_icon_button.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/inputbox/common_largeinputbox.dart';
import 'package:app/widgets/common_text_button.dart';

class WorkListSelectScreen extends StatefulWidget {
  const WorkListSelectScreen({super.key});

  @override
  _WorkListSelectScreenState createState() => _WorkListSelectScreenState();
}

class _WorkListSelectScreenState extends State<WorkListSelectScreen> {
  String? workListOption;

  void updateSustainCare(String option) {
    setState(() {
      if (workListOption == option) {
        workListOption = null; // 이미 선택된 항목을 다시 선택하면 선택 해제
      } else {
        workListOption = option;
      }
    });
  }

  final List<String> workListIcon = [
    'assets/images/maintenance_icon.png',
    'assets/images/clean_icon.png',
    'assets/images/repair_icon.png',
    'assets/images/etc_icon.png'
  ];
  final List<String> workText = ['정비', '청소', '수리', '기타'];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Header(title: '작업 내역', back: true),
      body: SingleChildScrollView(
        child: Center(
          child: Column(
            children: <Widget>[
              Container(
                width: MediaQuery.of(context).size.width * 0.8,
                height: MediaQuery.of(context).size.height * 0.4,
                child: GridView.count(
                  crossAxisCount: 2,
                  children: List.generate(
                      workListIcon.length,
                      (index) => CommonIconButton(
                            text: workText[index],
                            iconImage: workListIcon[index],
                            shape: BoxShape.circle,
                            isSelected: workListOption == workText[index],
                            onTap: () => updateSustainCare(workText[index]),
                          )),
                ),
              ),
              LargeInputBox(hintText: '내용을 입력해주세요'),
              const SizedBox(
                height: 30,
              ),
              CommonTextButton(text: '확인', onTap: () {Navigator.pushNamed(context, '/main');})
            ],
          ),
        ),
      ),
    );
  }
}
