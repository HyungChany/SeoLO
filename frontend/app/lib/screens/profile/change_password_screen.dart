import 'package:app/view_models/user/password_change_view_model.dart';
import 'package:app/widgets/button/common_text_button.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/inputbox/common_smallinputbox.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ChangePassword extends StatefulWidget {
  const ChangePassword({super.key});

  @override
  State<ChangePassword> createState() => _ChangePasswordState();
}

class _ChangePasswordState extends State<ChangePassword> {
  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<PasswordChangeViewModel>(context);
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
                child: SizedBox(
                  width: MediaQuery.of(context).size.width * 0.9,
                  child: Column(
                    children: [
                      SizedBox(height: 50,),
                      Text('비밀번호는 영문 대문자/소문자/숫자/특수문자를 섞어 8~16자리로 입력해주세요.'),
                      SizedBox(height: 30,),
                      SmallInputBox(
                          hintText: '새 비밀번호',
                          textInputAction: TextInputAction.next,
                          obscureText: true,
                          onChanged: (value) {
                            viewModel.setNewPwd(value);
                          }),
                      SizedBox(height: 20,),
                      SmallInputBox(
                          hintText: '새 비밀번호 확인',
                          textInputAction: TextInputAction.done,
                          obscureText: true,
                          onChanged: (value) {
                            viewModel.setCheckNewPwd(value);
                          }),
                    ],
                  ),
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
                    viewModel.passwordChange().then((_) {
                      if (viewModel.errorMessage == null) {
                        showDialog(
                            context: context,
                            barrierDismissible: false,
                            builder: (BuildContext context) {
                              return CommonDialog(
                                content: '비밀번호 변경이 완료되었습니다.\n다시 로그인 해주세요.',
                                buttonText: '확인',
                                buttonClick: () {
                                  Navigator.pushNamedAndRemoveUntil(
                                      context, '/login', (route) => false);
                                },
                              );
                            });
                      } else {

                        if (viewModel.errorMessage == 'JT') {
                          showDialog(
                              context: context,
                              barrierDismissible: false,
                              builder: (BuildContext context) {
                                return CommonDialog(
                                  content: '토큰이 만료되었습니다. 다시 로그인 해주세요.',
                                  buttonText: '확인',
                                  buttonClick: () {
                                    Navigator.pushNamedAndRemoveUntil(
                                        context, '/login', (route) => false);
                                  },
                                );
                              });
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
                      }
                    });
                  },
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
