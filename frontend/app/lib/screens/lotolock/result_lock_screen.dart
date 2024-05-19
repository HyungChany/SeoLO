import 'package:app/view_models/core/core_locked_view_model.dart';
import 'package:app/view_models/user/app_lock_state.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ResultLockScreen extends StatefulWidget {
  const ResultLockScreen({super.key});

  @override
  State<ResultLockScreen> createState() => _ResultLockScreenState();
}

class _ResultLockScreenState extends State<ResultLockScreen>
    with WidgetsBindingObserver {
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.paused ||
        state == AppLifecycleState.detached) {
      Provider.of<AppLockState>(context, listen: false)
          .lock(ModalRoute.of(context)!.settings.name!);
    }
  }

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
                            '잠금되었습니다.',
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
                            lockVM.errorMessage ?? '잠금에 실패하였습니다.',
                            style: TextStyle(
                                fontSize: 20, fontWeight: FontWeight.bold),
                          )
                        ],
                      ),
                    ),
                  ));
  }
}
