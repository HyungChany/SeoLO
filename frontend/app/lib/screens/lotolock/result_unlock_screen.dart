import 'package:app/view_models/core/core_unlock_view_model.dart';
import 'package:app/view_models/user/app_lock_state.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ResultUnlockScreen extends StatefulWidget {
  const ResultUnlockScreen({super.key});

  @override
  State<ResultUnlockScreen> createState() => _ResultUnlockScreenState();
}

class _ResultUnlockScreenState extends State<ResultUnlockScreen> with WidgetsBindingObserver {
  // @override
  // void initState() {
  //   super.initState();
  //   WidgetsBinding.instance.addObserver(this);
  // }
  //
  // @override
  // void dispose() {
  //   WidgetsBinding.instance.removeObserver(this);
  //   super.dispose();
  // }
  //
  // @override
  // void didChangeAppLifecycleState(AppLifecycleState state) {
  //   if (state == AppLifecycleState.paused ||
  //       state == AppLifecycleState.detached) {
  //     Provider.of<AppLockState>(context, listen: false)
  //         .lock(ModalRoute.of(context)!.settings.name!);
  //   }
  // }
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
                    child: SizedBox(
                      width: MediaQuery.of(context).size.width * 0.8,
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Image.asset('assets/images/success_loto.png'),
                          SizedBox(
                            height: 20,
                          ),
                          const Text(
                            '잠금 해제 되었습니다.',
                            style: TextStyle(
                                fontSize: 20, fontWeight: FontWeight.bold),
                          ),
                        ],
                      ),
                    ),
                  )
                : Center(
                    child: SizedBox(
                      width: MediaQuery.of(context).size.width * 0.8,
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Image.asset('assets/images/fail_loto.png'),
                          SizedBox(
                            height: 20,
                          ),
                          Text(
                            unlockVM.errorMessage ?? '잠금 해제에 실패하였습니다.',
                            style: TextStyle(
                                fontSize: 20, fontWeight: FontWeight.bold),
                          )
                        ],
                      ),
                    ),
                  ));
  }
}