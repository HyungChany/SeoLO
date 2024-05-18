import 'package:app/main.dart';
import 'package:app/view_models/user/my_info_view_model.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class MyInfo extends StatefulWidget {
  const MyInfo({super.key});

  @override
  State<MyInfo> createState() => _MyInfoState();
}

class _MyInfoState extends State<MyInfo> {

  @override
  void initState() {
    super.initState();
    final viewModel = Provider.of<MyInfoViewModel>(context, listen: false);
    WidgetsBinding.instance.addPostFrameCallback((_) {
      viewModel.myInfo().then((_) {
        if (viewModel.errorMessage == null) {
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
    });
  }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<MyInfoViewModel>(context);
    return SizedBox(
      width: MediaQuery.of(context).size.width * 0.7,
      height: MediaQuery.of(context).size.height * 0.08,
      child: Padding(
        padding: const EdgeInsets.only(left: 20.0),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            (viewModel.isLoading || viewModel.myInfoModel == null)
                ? Container(
              width: 80,
              height: 80,
              decoration: BoxDecoration(
                  color: gray200,
                  borderRadius: BorderRadius.circular(50)),
            )
                : ClipOval(
              child: Image.network(viewModel.myInfoModel!.employeeThum!,
                  width: 80, height: 80, fit: BoxFit.cover),
            ),
            Padding(
              padding: const EdgeInsets.only(left: 20.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  (viewModel.isLoading || viewModel.myInfoModel == null)
                      ? Container(
                    width: 150,
                    height: 20,
                    decoration: BoxDecoration(
                        color: gray200,
                        borderRadius: BorderRadius.circular(20)),
                  )
                      : Text(
                    viewModel.myInfoModel!.employeeTeam!,
                    style: const TextStyle(fontSize: 20),
                  ),
                  (viewModel.isLoading || viewModel.myInfoModel == null)
                      ? Container(
                    width: 150,
                    height: 20,
                    decoration: BoxDecoration(
                        color: gray200,
                        borderRadius: BorderRadius.circular(20)),
                  )
                      : Row(
                    children: [
                      Text(viewModel.myInfoModel!.employeeName!,
                          style: const TextStyle(fontSize: 20)),
                      const SizedBox(
                        width: 5,
                      ),
                      Text(viewModel.myInfoModel!.employeeTitle!,
                          style: const TextStyle(fontSize: 20))
                    ],
                  )
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}