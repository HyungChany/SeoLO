
import 'package:app/view_models/user/password_check_view_model.dart';
import 'package:app/widgets/button/common_text_button.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/inputbox/common_smallinputbox.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class CheckPassword extends StatefulWidget {
  const CheckPassword({super.key});

  @override
  _CheckPasswordState createState() => _CheckPasswordState();
}

class _CheckPasswordState extends State<CheckPassword> {
  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<PasswordCheckViewModel>(context);
    return Scaffold(
      appBar: const Header(title: '비밀번호 재설정', back: true),
      body: GestureDetector(
        onTap: () {
          FocusScope.of(context).unfocus();
        },
        child: Stack(
          children: [
            SingleChildScrollView(
              padding:
              EdgeInsets.only(bottom: MediaQuery.of(context).viewInsets.bottom),
              child: Center(
                child: Column(
                  children: [
                    SizedBox(height: 50,),
                    Text('비밀번호 재설정을 위해 현재 비밀번호를 입력해 주세요.'),
                    SizedBox(height: 30,),
                    SmallInputBox(
                        hintText: '현재 비밀번호',
                        textInputAction: TextInputAction.done,
                        obscureText: true,
                        onChanged: (value) {
                          viewModel.setNowPwd(value);
                        }),
                  ],
                ),
              ),
            ),
            Align(
              alignment: Alignment.bottomCenter,
              child: Padding(
                padding: EdgeInsets.only(bottom: 20),
                child: CommonTextButton(
                    text: '확인',
                    onTap: () {
                      viewModel.passwordCheck().then((_) {
                        if (viewModel.errorMessage == null) {
                          Navigator.pushReplacementNamed(context, '/changePassword');
                        } else {
                          showDialog(
                              context: context,
                              builder: (BuildContext context) {
                                return CommonDialog(
                                  content: viewModel.errorMessage!,
                                  buttonText: '확인',
                                );
                              });
                        }
                      });
                    }
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
