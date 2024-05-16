import 'package:app/main.dart';
import 'package:app/view_models/user/logout_view_model.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class LogoutButton extends StatelessWidget {
  const LogoutButton({super.key});

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<LogoutViewModel>(context);
    return InkWell(
      onTap: () {
        viewModel.logout().then((_) {
          if (viewModel.errorMessage == null) {
            Navigator.pushNamedAndRemoveUntil(
                context, '/login', (route) => false);
          } else {
            if (viewModel.errorMessage == 'JT') {
              showDialog(
                  context: context,
                  barrierDismissible: true,
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
      child: Container(
        width: MediaQuery.of(context).size.width * 0.25,
        height: MediaQuery.of(context).size.height * 0.045,
        decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(10.0),
            color: const Color.fromRGBO(217, 217, 217, 1),
            boxShadow: const [shadow]),
        child: const Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.logout),
            SizedBox(
              width: 5,
            ),
            Text(
              '로그아웃',
              style: TextStyle(fontWeight: FontWeight.bold),
            )
          ],
        ),
      ),
    );
  }
}
