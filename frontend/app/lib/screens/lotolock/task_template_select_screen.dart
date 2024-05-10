import 'package:app/view_models/core/core_issue_view_model.dart';
import 'package:app/view_models/loto/task_templates_view_model.dart';
import 'package:app/widgets/button/common_icon_button.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/inputbox/common_largeinputbox.dart';
import 'package:app/widgets/button/common_text_button.dart';
import 'package:provider/provider.dart';

class TaskTemplateSelectScreen extends StatefulWidget {
  const TaskTemplateSelectScreen({super.key});

  @override
  State<TaskTemplateSelectScreen> createState() =>
      _TaskTemplateSelectScreenState();
}

class _TaskTemplateSelectScreenState extends State<TaskTemplateSelectScreen> {
  String? taskOption;
  int selectId = 0;
  String selectName = '';
  String? selectPrecaution = '';

  void updateTaskTemplate(String option) {
    setState(() {
      if (taskOption == option) {
        taskOption = null; // 이미 선택된 항목을 다시 선택하면 선택 해제
        setState(() {
          selectId = 0;
          selectName = '';
          selectPrecaution = '';
        });
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

  @override
  void initState() {
    super.initState();
    Provider.of<TaskTemplatesViewModel>(context, listen: false).getTemplates();
    selectId = 0;
    selectName = '';
  }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<TaskTemplatesViewModel>(context);
    final coreViewModel = Provider.of<CoreIssueViewModel>(context);
    return Scaffold(
      appBar: const Header(title: '작업 내역', back: true),
      body: viewModel.isLoading
          ? const Center(
              child: CircularProgressIndicator(),
            )
          : SingleChildScrollView(
              child: Center(
                child: Column(
                  children: <Widget>[
                    SizedBox(
                      width: MediaQuery.of(context).size.width * 0.8,
                      height: MediaQuery.of(context).size.height * 0.4,
                      child: GridView.count(
                        crossAxisCount: 2,
                        children: List.generate(
                            taskIcon.length,
                            (index) => CommonIconButton(
                                text:
                                    viewModel.templates[index].taskTemplateName,
                                iconImage: taskIcon[index],
                                shape: BoxShape.circle,
                                isSelected: taskOption ==
                                    viewModel.templates[index].taskTemplateName,
                                onTap: () {
                                  selectId =
                                      viewModel.templates[index].taskTemplateId;
                                  selectName = viewModel
                                      .templates[index].taskTemplateName;
                                  selectPrecaution =
                                      viewModel.templates[index].taskPrecaution;
                                  updateTaskTemplate(viewModel
                                      .templates[index].taskTemplateName);
                                })),
                      ),
                    ),
                    LargeInputBox(
                      hintText: '내용을 입력해 주세요',
                      precaution: selectPrecaution,
                      onTextSaved: (text) {
                        selectPrecaution = text;
                      },
                    ),
                    const SizedBox(
                      height: 30,
                    ),
                    CommonTextButton(
                        text: '확인',
                        onTap: () {
                          if (selectId != 0) {
                            coreViewModel.setTaskPrecaution(selectPrecaution!);
                            coreViewModel.setTaskTemplateId(selectId);
                            coreViewModel.setTaskTemplateName(selectName);
                            Navigator.pushNamed(context, '/selectDay');
                          } else {
                            showDialog(
                                context: context,
                                barrierDismissible: true,
                                builder: (BuildContext context) {
                                  return const CommonDialog(
                                    content: '항목을 선택해 주세요.',
                                    buttonText: '확인',
                                  );
                                });
                          }
                        })
                  ],
                ),
              ),
            ),
    );
  }
}
