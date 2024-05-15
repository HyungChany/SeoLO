import 'package:app/main.dart';
import 'package:app/view_models/user/my_info_view_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class MyInfo extends StatefulWidget {
  const MyInfo({super.key});

  @override
  State<MyInfo> createState() => _MyInfoState();
}

class _MyInfoState extends State<MyInfo> {

  @override
  void initState() {
    final viewModel = Provider.of<MyInfoViewModel>(context, listen: false);
    super.initState();
    viewModel.myInfo();
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
            (viewModel.isLoading)
                ? Container(
                    width: 80,
                    height: 80,
                    decoration: BoxDecoration(
                        color: gray200,
                        borderRadius: BorderRadius.circular(50)),
                  )
                : ClipOval(
                  child: Image.network(viewModel.myInfoModel!.employeeThum,
                      width: 80, height: 80, fit: BoxFit.cover),
                ),
            Padding(
              padding: const EdgeInsets.only(left: 20.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  (viewModel.isLoading)
                      ? Container(
                          width: 150,
                          height: 20,
                          decoration: BoxDecoration(
                              color: gray200,
                              borderRadius: BorderRadius.circular(20)),
                        )
                      : Text(
                          viewModel.myInfoModel!.employeeTeam,
                          style: const TextStyle(fontSize: 20),
                        ),
                  (viewModel.isLoading)
                      ? Container(
                          width: 150,
                          height: 20,
                          decoration: BoxDecoration(
                              color: gray200,
                              borderRadius: BorderRadius.circular(20)),
                        )
                      : Row(
                          children: [
                            Text(viewModel.myInfoModel!.employeeName,
                                style: const TextStyle(fontSize: 20)),
                            const SizedBox(
                              width: 5,
                            ),
                            Text(viewModel.myInfoModel!.employeeTitle,
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
