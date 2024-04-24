import 'package:app/main.dart';
import 'package:app/widgets/lock/key_board_key.dart';
import 'package:flutter/material.dart';

class LockScreen extends StatefulWidget {
  @override
  _LockScreenState createState() => _LockScreenState();
}

class _LockScreenState extends State<LockScreen> {
  String pin = '';
  String content = '';

  @override
  void initState() {
    super.initState();
    pin = '';
    content = '암호를 입력해주세요.';
  }

  final keys = [
    ['1', '2', '3'],
    ['4', '5', '6'],
    ['7', '8', '9'],
    [Icon(Icons.fingerprint), '0', Icon(Icons.backspace_outlined)],
  ];

  onNumberPress(val) {
    setState(() {
      pin = pin + val;
    });

    // debugPrint(pin);
    // pin 길이가 처음으로 4가 되면 content 내용 변경
    if (pin != '1234' && pin.length == 4) content = '암호를 다시 입력해주세요.';
    // pin이 1234면 뒤로가기
    // pin 길이가 4인데 1234가 아니면 pin 초기화
    pin == '1234'
        ? Navigator.pop(context)
        : pin.length == 4
            ? pin = ''
            : null;
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
