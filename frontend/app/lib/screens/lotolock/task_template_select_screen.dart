import 'package:app/view_models/core/core_issue_view_model.dart';
import 'package:app/view_models/loto/task_templates_view_model.dart';
import 'package:app/widgets/button/common_icon_button.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/cupertino.dart';
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
    final viewModel = Provider.of<TaskTemplatesViewModel>(context, listen: false);
    WidgetsBinding.instance.addPostFrameCallback((_) {
      viewModel.getTemplates().then((_) {
        if (viewModel.errorMessage == null) {
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
                barrierDismissible: true,
                builder: (BuildContext context) {
                  return CommonDialog(
                    content: viewModel.errorMessage!,
                    buttonText: '확인',
                  );
                });
          }
        }
      });
      selectId = 0;
      selectName = '';
    });
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
          : GestureDetector(
        onTap: () {
          FocusScope.of(context).unfocus();
        },
            child: Stack(
                children: [
                  SingleChildScrollView(
                    child: Center(
                      child: Column(
                        children: <Widget>[
                          SizedBox(
                            height: 10,
                          ),
                          SizedBox(
                            width: MediaQuery.of(context).size.width * 0.8,
                            height: MediaQuery.of(context).size.height * 0.4,
                            child: GridView.count(
                              crossAxisCount: 2,
                              children: List.generate(
                                  taskIcon.length,
                                  (index) => CommonIconButton(
                                      text: viewModel
                                          .templates[index].taskTemplateName,
                                      iconImage: taskIcon[index],
                                      shape: BoxShape.circle,
                                      isSelected: taskOption ==
                                          viewModel
                                              .templates[index].taskTemplateName,
                                      onTap: () {
                                        selectId = viewModel
                                            .templates[index].taskTemplateId;
                                        selectName = viewModel
                                            .templates[index].taskTemplateName;
                                        selectPrecaution = viewModel
                                            .templates[index].taskPrecaution;
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
                        ],
                      ),
                    ),
                  ),
                  Align(
                    alignment: Alignment.bottomCenter,
                    child: Padding(
                        padding: const EdgeInsets.only(
                            bottom: 20, left: 50, right: 50),
                        child: CommonTextButton(
                            text: '확인',
                            onTap: () {
                              if (selectId != 0) {
                                coreViewModel
                                    .setTaskPrecaution(selectPrecaution!);
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
                            })),
                  ),
                ],
              ),
          ),
    );
  }
}
