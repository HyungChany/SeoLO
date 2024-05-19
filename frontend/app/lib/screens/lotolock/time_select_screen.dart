import 'package:app/view_models/user/app_lock_state.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';
import 'package:app/view_models/core/core_issue_view_model.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/button/common_text_button.dart';

class TimeSelect extends StatefulWidget {
  const TimeSelect({super.key});

  @override
  State<TimeSelect> createState() => _TimeSelectState();
}

class _TimeSelectState extends State<TimeSelect> with WidgetsBindingObserver {
  String endTime = DateFormat('HH:mm').format(DateTime.now());
  late DateTime currentDay;
  late int endHour;
  late int endMin;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    currentDay = DateTime.now();
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.paused ||
        state == AppLifecycleState.detached) {
      Provider.of<AppLockState>(context, listen: false)
          .lock(ModalRoute.of(context)!.settings.name!);
    }
  }

  @override
  Widget build(BuildContext context) {
    final coreViewModel = Provider.of<CoreIssueViewModel>(context);
    final initTime = DateFormat('HH:mm')
        .parse('${DateTime.now().hour}:${DateTime.now().minute}');

    DateTime endDay;
    endDay = DateFormat('yyyy-MM-dd').parse(coreViewModel.endDay!);

    return Scaffold(
      appBar: const Header(title: '예상 작업 종료 시간', back: true),
      body: Stack(
        children: [
          Center(
            child: SizedBox(
              height: 300,
              child: CupertinoDatePicker(
                mode: CupertinoDatePickerMode.time,
                initialDateTime: initTime,
                onDateTimeChanged: (DateTime date) {
                  setState(() {
                    endTime = DateFormat('HH:mm').format(date);
                    endHour = date.hour;
                    endMin = date.minute;
                  });
                },
              ),
            ),
          ),
          Align(
            alignment: Alignment.bottomCenter,
            child: Padding(
              padding: const EdgeInsets.only(bottom: 20, left: 50, right: 50),
              child: CommonTextButton(
                  text: '다음 단계',
                  onTap: () {
                    if (DateTime.now().isAfter(endDay
                        .add(Duration(hours: endHour, minutes: endMin)))) {
                      showDialog(
                          context: context,
                          barrierDismissible: true,
                          builder: (BuildContext context) {
                            return const CommonDialog(
                              content: '현재 이후의 시간를 선택해 주세요.',
                              buttonText: '확인',
                            );
                          });
                    } else {
                      coreViewModel.setEndTime(endTime);
                      Navigator.pushNamed(context, '/worklistCheck');
                    }
                  }),
            ),
          ),
        ],
      ),
    );
  }
}
