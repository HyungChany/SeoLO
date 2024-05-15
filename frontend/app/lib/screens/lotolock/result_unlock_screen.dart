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
                ? Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Image.asset('assets/images/success_loto.png'),
                        SizedBox(height: 20,),
                        const Text('잠금 해제 되었습니다.', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),),
                      ],
                    ),
                )
                : Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Image.asset('assets/images/fail_loto.png'),
                        Text(unlockVM.errorMessage ?? '잠금 해제에 실패하였습니다.')
                      ],
                    ),
                ));
  }
}
