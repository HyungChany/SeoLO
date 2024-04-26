import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/main/main_navi_page.dart';
import 'package:app/widgets/main/main_news_banner.dart';
import 'package:app/widgets/main/main_welcome_banner.dart';
import 'package:app/widgets/navigator/common_navigation_bar.dart';
import 'package:flutter/material.dart';

class MainScreen extends StatefulWidget {
  const MainScreen({super.key});

  _MainScreenState createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  int _selectedIndex = 0;
  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });

    if (index == 0) {
      Navigator.pushNamed(context, '/main');
    }
    if (index == 1) {
      Navigator.pushNamed(context, '/lock');
    }
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Header(title: '메인', back: true,),
      body: Center(
        child: SizedBox(
          width: MediaQuery.of(context).size.width * 0.85,
          child: Column(
            children: [
              SizedBox(height: 20,),
              MainWelcomeBanner(),
              SizedBox(height: 20,),
              MainNewsBanner(),
              SizedBox(height: 20,),
              MainNaviPage(),
              SizedBox(height: 20,),
              CustomBottomNavigationBar(
                selectedIndex: _selectedIndex,  // 현재 선택된 인덱스를 전달
                onItemTapped: _onItemTapped,  // 탭 선택 이벤트 처리 메소드를 전달
              ),
            ],
          ),
        ),
      ),
    );
  }
}
