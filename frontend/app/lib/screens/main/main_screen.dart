import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/main/main_navi_page.dart';
import 'package:app/widgets/main/main_news_banner.dart';
import 'package:app/widgets/main/main_welcome_banner.dart';
import 'package:flutter/material.dart';

class MainScreen extends StatelessWidget {
  const MainScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Header(title: '메인', back: true,),
      body: Center(
        child: SizedBox(
          width: MediaQuery.of(context).size.width * 0.85,
          child: const Column(
            children: [
              SizedBox(height: 20,),
              MainWelcomeBanner(),
              SizedBox(height: 20,),
              MainNewsBanner(),
              SizedBox(height: 20,),
              MainNaviPage(),
            ],
          ),
        ),
      ),
    );
  }
}
