import 'dart:convert';

import 'package:app/view_models/core/core_locked_view_model.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class LoadingLockScreen extends StatefulWidget {
  const LoadingLockScreen({super.key});

  @override
  State<LoadingLockScreen> createState() => _LoadingLockScreenState();
}

class _LoadingLockScreenState extends State<LoadingLockScreen> {
  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<CoreLockedViewModel>(context);
    if (viewModel.isLocking) {
      return Scaffold(
        body: Center(
            child: Image.asset(
          'assets/images/loading_icon.gif',
          width: 200,
          height: 200,
        )),
      );
    } else {
      viewModel.coreLocked().then((_) {
        if (viewModel.errorMessage == null) {
          Navigator.pushNamedAndRemoveUntil(
              context, '/resultLock', ModalRoute.withName('/main'));
        } else {
          if (viewModel.errorMessage == 'JT') {
            showDialog(
                context: context,
                barrierDismissible: true,
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
      return Container();
    }
  }
}
