import 'package:app/main.dart';
import 'package:app/view_models/user/my_info_view_model.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class MainWelcomeBanner extends StatefulWidget {
  const MainWelcomeBanner({super.key});

  @override
  _MainWelcomeBannerState createState() => _MainWelcomeBannerState();
}

class _MainWelcomeBannerState extends State<MainWelcomeBanner> {
  content() {
    final viewModel = Provider.of<MyInfoViewModel>(context, listen: false);
    return RichText(
        text: TextSpan(
            style: const TextStyle(
                fontSize: 23, fontWeight: FontWeight.bold, color: Colors.black),
            children: [
          TextSpan(text: viewModel.myInfoModel?.employeeName, style: TextStyle(color: blue400)),
          const TextSpan(text: '님, 오늘도 '),
          const TextSpan(text: '서로 ', style: TextStyle(color: safetyBlue)),
          const TextSpan(text: '지켰나요?')
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
