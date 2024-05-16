import 'package:app/view_models/core/core_issue_view_model.dart';
import 'package:app/view_models/loto/machine_view_model.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/checklist/check_banner.dart';
import 'package:app/widgets/checklist/select_list.dart';
import 'package:app/widgets/header/header.dart';
import 'package:provider/provider.dart';

class MachineSelectScreen extends StatefulWidget {
  const MachineSelectScreen({super.key});

  @override
  State<MachineSelectScreen> createState() => _MachineSelectScreenState();
}

class _MachineSelectScreenState extends State<MachineSelectScreen> {
  @override
  void initState() {
    final viewModel = Provider.of<MachineViewModel>(context, listen: false);
    super.initState();
    viewModel.loadInitialData().then((_){
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
    });
  }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<MachineViewModel>(context);
    final coreViewModel = Provider.of<CoreIssueViewModel>(context);
    return Scaffold(
      appBar: const Header(
        title: '설비 선택',
        back: true,
      ),
      body: viewModel.isLoading
          ? const Center(
              child: CircularProgressIndicator(),
            )
          : Center(
              child: SizedBox(
                width: MediaQuery.of(context).size.width,
                child: Column(
                  children: [
                    const CheckBanner(word: '설비', content: '를 선택 해주세요'),
                    const SizedBox(
                      height: 20,
                    ),
                    viewModel.machines.isEmpty
                        ? const Center(
                            child: Text('작업 가능한 설비가 없습니다.', style: TextStyle(fontSize: 20),),
                          )
                        : Expanded(
                            child: ListView.builder(
                            itemCount: viewModel.machines.length,
                            itemBuilder: (context, index) {
                              return SelectList(
                                title: viewModel.machines[index].machineName,
                                onTap: () {
                                  coreViewModel.setMachineId(
                                      viewModel.machines[index].machineId);
                                  coreViewModel.setMachineName(
                                      viewModel.machines[index].machineName);
                                  // coreViewModel.setMachineCode(
                                  //     viewModel.machines[index].machineCode);
                                  Navigator.pushNamed(context, '/taskTemplate');
                                },
                              );
                            },
                          ))
                  ],
                ),
              ),
            ),
    );
  }
}
