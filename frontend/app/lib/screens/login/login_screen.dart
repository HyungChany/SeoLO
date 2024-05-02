import 'package:app/widgets/common_text_button.dart';
import 'package:app/widgets/inputbox/common_smallinputbox.dart';
import 'package:flutter/material.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final shadow1 = const BoxShadow(
      color: Color.fromRGBO(255, 255, 255, 0.25),
      blurRadius: 5.29,
      offset: Offset(-5.29, 5.29));

  final shadow2 = const BoxShadow(
      color: Color.fromRGBO(182, 182, 182, 0.25),
      blurRadius: 5.29,
      offset: Offset(5.29, -5.29));

  loginBox() {
    return Container(
      width: MediaQuery.of(context).size.width * 0.9,
      height: MediaQuery.of(context).size.height * 0.6,
      decoration: (BoxDecoration(
          color: const Color.fromRGBO(49, 49, 49, 0.05),
          borderRadius: BorderRadius.circular(20.0),
          boxShadow: [shadow1, shadow2])),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              const SizedBox(height: 50),
              Image.asset('assets/images/login_logo.png'),
              const SmallInputBox(hintText: '회사 번호', textInputAction: TextInputAction.next,),
              const SmallInputBox(hintText: '사번', textInputAction: TextInputAction.next,),
              const SmallInputBox(hintText: '비밀번호', textInputAction: TextInputAction.done,),
              CommonTextButton(text: '로그인', onTap: () {}, width: MediaQuery.of(context).size.width * 0.82, height: MediaQuery.of(context).size.height * 0.06,)
            ],
          ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: Container(
        decoration: const BoxDecoration(
            image: DecorationImage(
                fit: BoxFit.cover,
                image: AssetImage('assets/images/login.png'))),
        child: Center(child: loginBox()),
      ),
    );
  }
}
