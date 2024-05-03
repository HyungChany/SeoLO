import 'package:app/main.dart';
import 'package:app/view_models/user/pin_change_view_model.dart';
import 'package:app/view_models/user/pin_login_view_model.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:app/widgets/lock/key_board_key.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ChangePinCheckScreen extends StatefulWidget {
  @override
  _ChangePinCheckScreenState createState() => _ChangePinCheckScreenState();
}

class _ChangePinCheckScreenState extends State<ChangePinCheckScreen> {
  String pin = '';
  String content = '';

  @override
  void initState() {
    super.initState();
    pin = '';
    content = '새로운 암호를 한 번 더 입력해 주세요.';
  }

  final keys = [
    ['1', '2', '3'],
    ['4', '5', '6'],
    ['7', '8', '9'],
    ['', '0', Icon(Icons.backspace_outlined)],
  ];

  onNumberPress(val) {
    final viewModel = Provider.of<PinChangeViewModel>(context, listen: false);
    setState(() {
      pin = pin + val;
      viewModel.setCheckNewPin(pin);
    });

    if (pin.length == 4) {
      if (viewModel.newPin != viewModel.checkNewPin) {
        setState(() {
          pin = '';
          content = 'pin 번호가 일치하지 않습니다.';
        });
      } else {
        if (!viewModel.isLoading) {
          viewModel.pinChange().then((_) {
            if (viewModel.errorMessage == null) {
              showDialog(
                  context: context,
                  barrierDismissible: false,
                  builder: (BuildContext context) {
                    return CommonDialog(
                      content: 'pin 번호 변경이 완료되었습니다.',
                      buttonText: '확인',
                      buttonClick: () {
                        Navigator.pushNamedAndRemoveUntil(
                            context, '/main', (route) => false);
                      },
                    );
                  });
            } else {
              setState(() {
                pin = '';
                content = viewModel.errorMessage!;
              });
            }
          });
        }
      }
    }
  }

  onBackspacePress(val) {
    setState(() {
      pin = pin.substring(0, pin.length - 1);
    });
  }

  renderKeyboard() {
    return keys
        .map(
          (x) => Container(
        color: blue100.withOpacity(0.5),
        child: Row(
          children: x.map((y) {
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
    )
        .toList();
  }

  renderText() {
    TextStyle styleTitle =
    TextStyle(fontSize: 30, fontWeight: FontWeight.w700, color: blue400);

    TextStyle styleContent = TextStyle(
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
            SizedBox(
              height: 10,
            ),
            Text(
              content,
              style: styleContent,
            ),
            SizedBox(
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
                          : Color.fromRGBO(227, 227, 227, 1),
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
