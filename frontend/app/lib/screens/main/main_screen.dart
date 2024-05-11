import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/main/main_navi_page.dart';
import 'package:app/widgets/main/main_news_banner.dart';
import 'package:app/widgets/main/main_welcome_banner.dart';
import 'package:app/widgets/navigator/common_navigation_bar.dart';
import 'package:flutter/material.dart';

class MainScreen extends StatefulWidget {
  const MainScreen({super.key});

  @override
  State<MainScreen> createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  int _selectedIndex = 0;

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });

    if (index == 0) {
      Navigator.pushReplacementNamed(context, '/main');
    }
    if (index == 1) {
      Navigator.pushNamed(context, '/bluetooth');
      setState(() {
        _selectedIndex = 0;
      });
    }
    if (index == 2) {
      Navigator.pushReplacementNamed(context, '/profile');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
          child: SizedBox(
            width: MediaQuery
                .of(context)
                .size
                .width * 0.85,
            child: const Column(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                MainWelcomeBanner(),
                MainNewsBanner(),
                MainNaviPage(),
              ],
            ),
          ),
        ),
        bottomNavigationBar: CustomBottomNavigationBar(
          selectedIndex: _selectedIndex,
          onItemTapped: _onItemTapped,
        ) ,
    );
  }
}
