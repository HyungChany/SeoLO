
import 'package:app/widgets/button/common_text_button.dart';
import 'package:app/widgets/inputbox/common_smallinputbox.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/navigator/common_navigation_bar.dart';

import 'package:app/widgets/checklist/check_list.dart';
class TestScreen extends StatefulWidget {
  const TestScreen({super.key});

  @override
  State<TestScreen> createState() => _TestScreenState();
}

class _TestScreenState extends State<TestScreen> {
  int _selectedIndex = 0;  // 현재 선택된 탭의 인덱스를 저장하는 변수
  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;  // 선택된 탭의 인덱스를 업데이트
    });

    if (index == 0) { // 'Home' 탭이 선택될 때
      Navigator.pushNamed(context, '/checklist');
    }
    if (index == 1) {
      Navigator.pushNamed(context, '/otherworklistcheck');
    }
    if (index == 2) {
      Navigator.pushNamed(context, '/profile');
    }
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Padding(
          padding: const EdgeInsets.all(16),
          child: Center(
            child: Wrap(
              spacing: 16,
              runSpacing: 16,
              alignment: WrapAlignment.start,
              children: [
                CommonTextButton(text: '다음 단계', onTap: () {}),
                ElevatedButton(
                  onPressed: () {
                    Navigator.pushNamed(context, '/lock');
                  },
                  child: const Text('잠금화면'),
                ),
                ElevatedButton(
                  onPressed: () {
                    Navigator.pushNamed(context, '/main');
                  },
                  child: const Text('메인화면'),
                ),
                ElevatedButton(
                  onPressed: () {
                    Navigator.pushNamed(context, '/login');
                  },
                  child: const Text('로그인'),
                ),
                CustomBottomNavigationBar(
                  selectedIndex: _selectedIndex,  // 현재 선택된 인덱스를 전달
                  onItemTapped: _onItemTapped,  // 탭 선택 이벤트 처리 메소드를 전달
                ),
                const CheckBoxList(title: '체크리스트 확인사항',),
                // SmallInputBox(hintText: '메롱')
              ],
            ),
          ),
        ));
  }
}
