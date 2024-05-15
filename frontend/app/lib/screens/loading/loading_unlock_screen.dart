import 'package:app/view_models/core/core_unlock_view_model.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class LoadingUnlockScreen extends StatefulWidget {
  const LoadingUnlockScreen({super.key});

  @override
  State<LoadingUnlockScreen> createState() => _LoadingUnlockScreenState();
}

class _LoadingUnlockScreenState extends State<LoadingUnlockScreen> {

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<CoreUnlockViewModel>(context);
    if (viewModel.isUnlocking) {
      return Scaffold(
        body: Center(
          child: Image.asset(
            'assets/images/loading_icon.gif',
            width: 200,
            height: 200,
          ),
        ),
      );
    } else {
      WidgetsBinding.instance.addPostFrameCallback((_) {
        Navigator.pop(context);
      });
      // 여기서는 임시로 Container를 반환합니다. 실제로 사용할 다른 화면을 반환해야 합니다.
      return Container();
    }
  }
}
