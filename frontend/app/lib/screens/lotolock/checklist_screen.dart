import 'package:app/widgets/checklist/check_list.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/checklist/check_banner.dart';
import 'package:app/widgets/button/common_text_button.dart';

class CheckScreen extends StatefulWidget {
  @override
  _CheckScreenState createState() => _CheckScreenState();
}

class _CheckScreenState extends State<CheckScreen> {
  late List<bool> _isCheckedList;

  @override
  void initState() {
    super.initState();
    _isCheckedList = List.filled(checkList.length, false);
  }

  bool _allChecked() {
    return _isCheckedList.every((isChecked) => isChecked);
  }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<ChecklistViewModel>(context);
    return Scaffold(
      appBar: const Header(
        title: '체크리스트',
        back: true,
      ),
      body: Center(
        child: SizedBox(
          width: MediaQuery.of(context).size.width * 0.95,
          child: Column(
            children: [
              CheckBanner(
                word: '체크리스트',
                content: '의 모든 항목을 확인해주세요',
              ),
              SizedBox(
                height: 20,
              ),
              Expanded(
                child: ListView.builder(
                  itemCount: checkList.length,
                  itemBuilder: (context, index) {
                    return CheckBoxList(
                      title: checkList[index],
                      onChecked: (isChecked) {
                        setState(() {
                          _isCheckedList[index] = isChecked;
                        });
                      },
                    );
                  },
                ),
              ),
              CommonTextButton(
                text: '다음 단계',
                onTap: _allChecked()
                    ? () {
                        Navigator.pushNamed(context, '/worklist');
                      }
                    : () {
                          showDialog(
                          context: context,
                          barrierDismissible: true,
                          builder: (BuildContext context) {
                            return CommonDialog(
                              content: '체크리스트의 모든 항목에 체크해 주세요.'
                              buttonText: '확인',
                            );
                          });
                    }
              ),
              SizedBox(
                height: 20,
              )
            ],
          ),
        ),
      ),
    );
  }
}