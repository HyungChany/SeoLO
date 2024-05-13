import 'package:app/view_models/core/core_unlock_view_model.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ResultUnlockScreen extends StatefulWidget {
  const ResultUnlockScreen({super.key});

  @override
  State<ResultUnlockScreen> createState() => _ResultUnlockScreenState();
}

class _ResultUnlockScreenState extends State<ResultUnlockScreen> {
  @override
  Widget build(BuildContext context) {
    final unlockVM = Provider.of<CoreUnlockViewModel>(context);

    return Scaffold(
        body: (unlockVM.isLoading)
            ? const Center(
                child: CircularProgressIndicator(),
              )
            : (unlockVM.errorMessage == null)
                ? Column(
                    children: [
                      Image.asset('assets/images/success_loto.png'),
                      const Text('잠금 해제 되었습니다.'),
                    ],
                  )
                : Column(
                    children: [
                      Image.asset('assets/images/fail_loto.png'),
                      Text(unlockVM.errorMessage ?? '잠금 해제에 실패하였습니다.')
                    ],
                  ));
  }
}
