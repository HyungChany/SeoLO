import 'package:app/view_models/user/app_lock_state.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/loto_process/loto_definition.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class LotoProcessScreen extends StatefulWidget {
  const LotoProcessScreen({super.key});

  @override
  State<LotoProcessScreen> createState() => _LotoProcessState();
}

class _LotoProcessState extends State<LotoProcessScreen>
    with WidgetsBindingObserver {
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
  //         .lock('/lotoProcess');
  //   }
  // }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const Header(
        title: 'LOTO 정의',
        back: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(15.0),
        child: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text(
                'LOTO란?',
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              ),
              LotoDefinition(),
              const Text('LOTO 작업절차',
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
              Image.asset(
                'assets/images/loto_process_character.png',
              )
            ],
          ),
        ),
      ),
    );
  }
}
