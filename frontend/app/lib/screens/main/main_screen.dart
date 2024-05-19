import 'package:app/view_models/user/app_lock_state.dart';
import 'package:app/widgets/main/main_navi_page.dart';
import 'package:app/widgets/main/main_news_banner.dart';
import 'package:app/widgets/main/main_welcome_banner.dart';
import 'package:app/widgets/navigator/common_navigation_bar.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class MainScreen extends StatefulWidget {
  const MainScreen({super.key});

  @override
  State<MainScreen> createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> with WidgetsBindingObserver {
  int _selectedIndex = 0;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.paused || state == AppLifecycleState.detached) {
      Provider.of<AppLockState>(context, listen: false).lock(ModalRoute.of(context)!.settings.name!);
    }
  }

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
                SizedBox(height: 10,),
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
