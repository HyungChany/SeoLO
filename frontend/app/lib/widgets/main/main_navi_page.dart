import 'package:app/main.dart';
import 'package:app/widgets/button/common_icon_button.dart';
import 'package:flutter/material.dart';

class MainNaviPage extends StatefulWidget {
  const MainNaviPage({super.key});

  @override
  State<MainNaviPage> createState() => _MainNaviPageState();
}

class _MainNaviPageState extends State<MainNaviPage> {
  final List<String> pageIcon = [
    'assets/images/nfc_tag_icon.png',
    'assets/images/work_report_icon.png',
    'assets/images/loto_lock_icon.png',
    'assets/images/loto_process_icon.png'
  ];

  final List<String> pageText = ['BLUETOOTH', '내 작업 일지', 'LOTO 잠금', 'LOTO 절차'];

  final List<String> pageTap = ['/bluetooth', '/profile', '/checklist', '/lotoProcess'];

  @override
  Widget build(BuildContext context) {
    return Container(
      height: MediaQuery.of(context).size.height * 0.5,
      decoration: BoxDecoration(
        color: const Color.fromRGBO(255, 250, 250, 1),
        borderRadius: BorderRadius.circular(10.0),
        boxShadow: const [shadow],
      ),
      child: Center(
        child: GridView.count(
          crossAxisCount: 2,
          mainAxisSpacing: 35.0,
          shrinkWrap: true,
          children: List.generate(
            pageIcon.length,
                (index) => CommonIconButton(
              text: pageText[index],
              iconImage: pageIcon[index],
              shape: BoxShape.rectangle,
              onTap: () {
                Navigator.pushNamed(context, pageTap[index]);
              },
            ),
          ),
        ),
      ),
    );
  }

}
