import 'package:app/main.dart';
import 'package:flutter/material.dart';

class WelcomeBanner extends StatefulWidget {
  const WelcomeBanner({super.key});

  @override
  _WelcomeBannerState createState() => _WelcomeBannerState();
}

class _WelcomeBannerState extends State<WelcomeBanner> {

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
          boxShadow: const [
            BoxShadow(
                color: shadow,
                blurRadius: 4.0,
                spreadRadius: 4.0,
                offset: Offset(
                  3,
                  3,
                ))
          ]),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          content(),
          Text(' '),
          const Image(image: AssetImage('assets/images/seolo_character.png'))
        ],
      ),
    );
  }
}
