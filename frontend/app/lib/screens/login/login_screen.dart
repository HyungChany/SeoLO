import 'package:app/view_models/user/login_view_model.dart';
import 'package:app/widgets/button/common_text_button.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:app/widgets/inputbox/common_smallinputbox.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _storage = FlutterSecureStorage();
  final shadow1 = const BoxShadow(
      color: Color.fromRGBO(255, 255, 255, 0.25),
      blurRadius: 5.29,
      offset: Offset(-5.29, 5.29));

  final shadow2 = const BoxShadow(
      color: Color.fromRGBO(182, 182, 182, 0.25),
      blurRadius: 5.29,
      offset: Offset(5.29, -5.29));

  loginBox() {
    final viewModel = Provider.of<LoginViewModel>(context);
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
          SmallInputBox(
            hintText: '회사 번호',
            textInputAction: TextInputAction.next,
            onChanged: (value) => viewModel.setCompanyCode(value),
          ),
          SmallInputBox(
            hintText: '사번',
            textInputAction: TextInputAction.next,
            onChanged: (value) => viewModel.setUsername(value),
          ),
          SmallInputBox(
            hintText: '비밀번호',
            textInputAction: TextInputAction.done,
            onChanged: (value) => viewModel.setPassword(value),
            obscureText: true,
          ),
          CommonTextButton(
            text: '로그인',
            onTap: () {
              if (!viewModel.isLoading) {
                viewModel.login().then((_) {
                  if (viewModel.errorMessage == null) {
                    Navigator.pushReplacementNamed(context, '/main');
                  } else {
                    showDialog(
                        context: context,
                        barrierDismissible: true,
                        builder: (BuildContext context) {
                          return CommonDialog(
                            content: viewModel.errorMessage!,
                            buttonText: '확인',
                          );
                        });
                  }
                });
              }
            },
            width: MediaQuery.of(context).size.width * 0.82,
            height: MediaQuery.of(context).size.height * 0.06,
          )
        ],
      ),
    );
  }

  _asyncMethod() async {
    final viewModel = Provider.of<LoginViewModel>(context, listen: false);
    String? jsessionid = await _storage.read(key: 'jsessionid');
    if (jsessionid != null) {
      await viewModel.login().then((_) {
        debugPrint('원래 id : $jsessionid');
        debugPrint('새로운 id : ${viewModel.jsessionid}');
        // login screen에서 login api 요청을 보내서 storage에 저장된 값이랑 비교
        // 만약 다르다면 storage에 있는 값 업데이트
        if (viewModel.jsessionid != jsessionid) {
          _storage.delete(key: 'jsessionid');
          _storage.write(key: 'jsessionid', value: viewModel.jsessionid);
        }
      });
      if (!mounted) return;
      Navigator.pushReplacementNamed(context, '/pinLogin');
    }
  }

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      _asyncMethod();
    });
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
