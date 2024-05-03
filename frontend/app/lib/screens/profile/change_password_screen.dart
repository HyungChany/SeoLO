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
  _ChangePasswordState createState() => _ChangePasswordState();
}

class _ChangePasswordState extends State<ChangePassword> {
  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<PasswordChangeViewModel>(context);
    return Scaffold(
      appBar: const Header(title: '비밀번호 재설정', back: true),
      body: Column(
        children: [
          SmallInputBox(
              hintText: '새 비밀번호',
              textInputAction: TextInputAction.next,
              onChanged: (value) {
                viewModel.setNewPwd(value);
              }),
          SmallInputBox(
              hintText: '새 비밀번호 확인',
              textInputAction: TextInputAction.done,
              onChanged: (value) {
                viewModel.setCheckNewPwd(value);
              }),
          CommonTextButton(
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
              })
        ],
      ),
    );
  }
}
