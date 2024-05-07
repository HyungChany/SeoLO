import 'package:app/main.dart';
import 'package:app/view_models/user/pin_login_view_model.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:app/widgets/lock/key_board_key.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:local_auth/local_auth.dart';
import 'package:provider/provider.dart';

class PinLoginScreen extends StatefulWidget {
  const PinLoginScreen({super.key});

  @override
  _PinLoginScreenState createState() => _PinLoginScreenState();
}

class _PinLoginScreenState extends State<PinLoginScreen> {
  final _storage = const FlutterSecureStorage();
  final _auth = LocalAuthentication();

  String pin = '';
  String content = '';
  int failCount = 0;

  @override
  void initState() {
    super.initState();
    pin = '';
    content = '암호를 입력해 주세요.';
    failCount = 0;
  }

  final keys = [
    ['1', '2', '3'],
    ['4', '5', '6'],
    ['7', '8', '9'],
    [Icon(Icons.fingerprint), '0', Icon(Icons.backspace_outlined)],
  ];

  onNumberPress(val) {
    final viewModel = Provider.of<PinLoginViewModel>(context, listen: false);
    setState(() {
      pin = pin + val;
      viewModel.setPin(pin);
    });

    if (pin.length == 4) {
      if (!viewModel.isLoading) {
        viewModel.pinLogin().then((_) {
          if (viewModel.errorMessage == null) {
            Navigator.pushReplacementNamed(context, '/main');
            setState(() {
              pin = '';
              failCount = 0;
            });
          } else {
            setState(() {
              pin = '';
              failCount += 1;
              content = failCount == 5 ? '' : '${viewModel.errorMessage!} ($failCount/5)';
            });
            failCount == 3
                ? showDialog(
                    context: context,
                    barrierDismissible: false,
                    builder: (BuildContext context) {
                      return const CommonDialog(
                        content: 'pin 번호를 5번 틀릴 시 계정이 잠깁니다.',
                        buttonText: '확인',
                      );
                    })
                : null;
            failCount == 5
            ? showDialog(
                context: context,
                barrierDismissible: false,
                builder: (BuildContext context) {
                  return CommonDialog(
                    content: viewModel.errorMessage!,
                    buttonText: '확인',
                    buttonClick: () {
                      _storage.deleteAll();
                      Navigator.pushNamedAndRemoveUntil(
                          context, '/login', (route) => false);
                    },
                  );
                })
                : null;
          }
        });
      }
    }
  }

  onBackspacePress(val) {
    setState(() {
      pin = pin.substring(0, pin.length - 1);
    });
  }

  onFingerprintPress() {

  }

  gradient1() {
    return BoxDecoration(
      gradient: LinearGradient(
        colors: [
          Colors.white.withOpacity(0.5),
          const Color.fromRGBO(215, 223, 243, 0.5)
        ],
        begin: Alignment.topCenter,
        end: Alignment.bottomCenter,
      ),
    );
  }

  gradient2() {
    return const BoxDecoration(
      gradient: LinearGradient(
        colors: [
          Color.fromRGBO(215, 223, 243, 0.5),
          Color.fromRGBO(175, 190, 240, 0.5)
        ],
        begin: Alignment.topCenter,
        end: Alignment.bottomCenter,
      ),
    );
  }

  gradient3() {
    return const BoxDecoration(
      gradient: LinearGradient(
        colors: [
          Color.fromRGBO(175, 190, 240, 0.5),
          Color.fromRGBO(135, 157, 238, 0.5)
        ],
        begin: Alignment.topCenter,
        end: Alignment.bottomCenter,
      ),
    );
  }

  gradient4() {
    return const BoxDecoration(
      gradient: LinearGradient(
        colors: [Color.fromRGBO(135, 157, 238, 0.5), blue100],
        begin: Alignment.topCenter,
        end: Alignment.bottomCenter,
      ),
    );
  }

  renderKeyboard() {
    List keyboardRows = [];
    for (int i = 0; i < keys.length; i++) {
      BoxDecoration decoration;
      if (i % 4 == 0) {
        decoration = gradient1();
      } else if (i % 4 == 1) {
        decoration = gradient2();
      } else if (i % 4 == 2) {
        decoration = gradient3();
      } else {
        decoration = gradient4();
      }
      keyboardRows.add(
        Container(
          decoration: decoration,
          child: Row(
            children: keys[i].map((y) {
              return Expanded(
                child: KeyboardKey(
                  label: y,
                  onTap: y is Widget ? onBackspacePress : onNumberPress,
                  value: y,
                ),
              );
            }).toList(),
          ),
        ),
      );
    }
    return keyboardRows;
  }

  renderText() {
    TextStyle styleTitle = const
        TextStyle(fontSize: 30, fontWeight: FontWeight.w700, color: blue400);

    TextStyle styleContent = const TextStyle(
        fontSize: 15, fontWeight: FontWeight.w500, color: Colors.black);

    return Expanded(
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              '암호 입력',
              style: styleTitle,
            ),
            const SizedBox(
              height: 10,
            ),
            Text(
              content,
              style: styleContent,
            ),
            const SizedBox(
              height: 20,
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                for (num i = 1; i < 5; i++)
                  Text(
                    '*',
                    style: TextStyle(
                      color: pin.length >= i
                          ? blue100
                          : const Color.fromRGBO(227, 227, 227, 1),
                      fontWeight: FontWeight.bold,
                      fontSize: 50.0,
                    ),
                  ),
              ],
            )
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.end,
          children: [
            renderText(),
            ...renderKeyboard(),
          ],
        ),
      ),
    );
  }
}
