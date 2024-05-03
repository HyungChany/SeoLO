import 'package:app/widgets/checklist/check_list.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/checklist/check_banner.dart';
import 'package:app/widgets/button/common_text_button.dart';

// import 'package:app/widgets/checklist/check_list.dart';
class CheckScreen extends StatefulWidget {
  @override
  _CheckScreenState createState() => _CheckScreenState();
}

class _CheckScreenState extends State<CheckScreen> {
  @override
  Widget build(BuildContext context) {
    final List<String> checkList = [
      '작업장 안전 규칙 1',
      '작업장 안전 규칙 2',
      '작업장 안전 규칙 3',
      '작업장 안전 규칙 4',
      '작업장 안전 규칙 5',
      '작업장 안전 규칙 6'
    ];
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
                // Column 내에 ListView를 사용할 경우 Expanded 필요
                child: ListView.builder(
                  itemCount: checkList.length,
                  itemBuilder: (context, index) {
                    return CheckBoxList(
                      title: checkList[index], // 각 항목의 제목을 CheckBoxList에 전달
                    );
                  },
                ),
              ),
              CommonTextButton(
                  text: '다음 단계',
                  onTap: () {
                    Navigator.pushNamed(context, '/main');
                  }),
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
