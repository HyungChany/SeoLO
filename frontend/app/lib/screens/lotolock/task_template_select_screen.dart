import 'package:app/widgets/button/common_icon_button.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/inputbox/common_largeinputbox.dart';
import 'package:app/widgets/button/common_text_button.dart';

class TaskTemplateSelectScreen extends StatefulWidget {
  const TaskTemplateSelectScreen({super.key});

  @override
  State<TaskTemplateSelectScreen> createState() => _TaskTemplateSelectScreenState();
}

class _TaskTemplateSelectScreenState extends State<TaskTemplateSelectScreen> {
  String? taskOption;

  void updateTaskTemplate(String option) {
    setState(() {
      if (taskOption == option) {
        taskOption = null; // 이미 선택된 항목을 다시 선택하면 선택 해제
      } else {
        taskOption = option;
      }
    });
  }

  final List<String> taskIcon = [
    'assets/images/maintenance_icon.png',
    'assets/images/clean_icon.png',
    'assets/images/repair_icon.png',
    'assets/images/etc_icon.png'
  ];
  final List<String> taskName = ['정비', '청소', '수리', '기타'];

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
                      taskIcon.length,
                      (index) => CommonIconButton(
                            text: taskName[index],
                            iconImage: taskIcon[index],
                            shape: BoxShape.circle,
                            isSelected: taskOption == taskName[index],
                            onTap: () => updateTaskTemplate(taskName[index]),
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
