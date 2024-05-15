import 'package:app/view_models/core/core_locked_view_model.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ResultLockScreen extends StatefulWidget {
  const ResultLockScreen({super.key});

  @override
  State<ResultLockScreen> createState() => _ResultLockScreenState();
}

class _ResultLockScreenState extends State<ResultLockScreen> {
  
  @override
  Widget build(BuildContext context) {
    final lockVM = Provider.of<CoreLockedViewModel>(context);

    return Scaffold(
        body: (lockVM.isLoading)
            ? const Center(
                child: CircularProgressIndicator(),
              )
            : (lockVM.errorMessage == null)
                ? Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Image.asset('assets/images/success_loto.png'),
                        const Text('잠금되었습니다.'),
                      ],
                    ),
                )
                : Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                      children: [Image.asset('assets/images/fail_loto.png'),
                      Text(lockVM.errorMessage ?? '잠금에 실패하였습니다.')],
                    ),
                ));
  }
}
