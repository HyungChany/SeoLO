import 'package:app/main.dart';
import 'package:flutter/material.dart';

class MainWelcomeBanner extends StatefulWidget {
  const MainWelcomeBanner({super.key});

  @override
  _MainWelcomeBannerState createState() => _MainWelcomeBannerState();
}

class _MainWelcomeBannerState extends State<MainWelcomeBanner> {
  content() {
    return RichText(
        text: const TextSpan(
            style: TextStyle(
                fontSize: 23, fontWeight: FontWeight.bold, color: Colors.black),
            children: [
          TextSpan(text: 'user', style: TextStyle(color: blue100)),
          TextSpan(text: '님, 오늘도 '),
          TextSpan(text: '서로 ', style: TextStyle(color: safetyBlue)),
          TextSpan(text: '지켰나요?')
        ]));
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      height: MediaQuery.of(context).size.height * 0.1,
      decoration: BoxDecoration(
          color: gray200,
          borderRadius: BorderRadius.circular(10.0),
          boxShadow: const [shadow]),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          content(),
          Text(' '),
          Image.asset('assets/images/seolo_character.png', width: 30, height: 50,)
        ],
      ),
    );
  }
}
