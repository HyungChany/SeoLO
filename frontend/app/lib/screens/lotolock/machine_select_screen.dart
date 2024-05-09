import 'package:app/view_models/core/core_issue_view_model.dart';
import 'package:app/view_models/loto/machine_view_model.dart';
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
    super.initState();
    Provider.of<MachineViewModel>(context, listen: false).loadInitialData();
  }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<MachineViewModel>(context);
    final coreViewModel = Provider.of<CoreIssueViewModel>(context);
    return Scaffold(
      appBar: Header(
        title: '설비 선택',
        back: true,
      ),
      body: Center(
        child: SizedBox(
          width: MediaQuery
              .of(context)
              .size
              .width,
          child: Column(
            children: [
              CheckBanner(word: '설비', content: '를 선택 해주세요'),
              SizedBox(
                height: 20,
              ),
              Expanded(
                  child: ListView.builder(
                    itemCount: viewModel.machines.length,
                    itemBuilder: (context, index) {
                      return SelectList(
                        title: viewModel.machines[index].machineName,
                        onTap: () {
                          coreViewModel.setMachineId(viewModel.machines[index].machineId);
                          coreViewModel.setMachineName(viewModel.machines[index].machineName);
                          coreViewModel.setMachineCode(viewModel.machines[index].machineCode);
                          Navigator.pushNamed(context, '/taskTemplate');
                        },);
                    },
                  ))
            ],
          ),
        ),
      ),
    );
  }
}
