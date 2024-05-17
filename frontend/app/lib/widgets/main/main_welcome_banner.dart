import 'package:app/main.dart';
import 'package:app/view_models/user/my_info_view_model.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class MainWelcomeBanner extends StatefulWidget {
  const MainWelcomeBanner({super.key});

  @override
  State<MainWelcomeBanner> createState() => _MainWelcomeBannerState();
}

class _MainWelcomeBannerState extends State<MainWelcomeBanner> {
  @override
  void initState() {
    super.initState();
    final viewModel = Provider.of<MyInfoViewModel>(context, listen: false);
    WidgetsBinding.instance.addPostFrameCallback((_) {
      viewModel.myInfo().then((_) {
        if (viewModel.errorMessage == null) {
        } else {
          if (viewModel.errorMessage == 'JT') {
            showDialog(
                context: context,
                barrierDismissible: false,
                builder: (BuildContext context) {
                  return CommonDialog(
                    content: '토큰이 만료되었습니다. 다시 로그인 해주세요.',
                    buttonText: '확인',
                    buttonClick: () {
                      Navigator.pushNamedAndRemoveUntil(
                          context, '/login', (route) => false);
                    },
                  );
                });
          } else {
            showDialog(
                context: context,
                barrierDismissible: true,
                builder: (BuildContext context) {
                  return CommonDialog(
                    content: viewModel.errorMessage!,
                    buttonText: '확인',
                  );
                });
          }
        }
      });
    });
  }

  Widget content() {
    final viewModel = Provider.of<MyInfoViewModel>(context);
    return RichText(
        text: TextSpan(
            style: const TextStyle(
                fontSize: 23, fontWeight: FontWeight.bold, color: Colors.black),
            children: [
          viewModel.isLoading
              ? TextSpan(text: '           ')
              : TextSpan(
                  text: (viewModel.myInfoModel != null)
                      ? viewModel.myInfoModel!.employeeName
                      : '',
                  style: TextStyle(color: blue400)),
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
          Image.asset(
            'assets/images/seolo_character.png',
            width: 30,
            height: 50,
          )
        ],
      ),
    );
  }
}
