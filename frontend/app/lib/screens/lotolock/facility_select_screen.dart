import 'package:app/view_models/core/core_issue_view_model.dart';
import 'package:app/view_models/loto/facility_view_model.dart';
import 'package:app/view_models/loto/machine_view_model.dart';
import 'package:app/view_models/user/app_lock_state.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/checklist/check_banner.dart';
import 'package:app/widgets/checklist/select_list.dart';
import 'package:app/widgets/header/header.dart';
import 'package:provider/provider.dart';

class FacilitySelectScreen extends StatefulWidget {
  const FacilitySelectScreen({super.key});

  @override
  State<FacilitySelectScreen> createState() => _FacilitySelectScreenState();
}

class _FacilitySelectScreenState extends State<FacilitySelectScreen> with WidgetsBindingObserver {
  @override
  void initState() {
    super.initState();
    // WidgetsBinding.instance.addObserver(this);
    final viewModel = Provider.of<FacilityViewModel>(context, listen: false);
    WidgetsBinding.instance.addPostFrameCallback((_) {
      Provider.of<CoreIssueViewModel>(context, listen: false).fetchMyInfo();
      viewModel.loadInitialData().then((_) {
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

  // @override
  // void dispose() {
  //   WidgetsBinding.instance.removeObserver(this);
  //   super.dispose();
  // }
  //
  // @override
  // void didChangeAppLifecycleState(AppLifecycleState state) {
  //   if (state == AppLifecycleState.paused ||
  //       state == AppLifecycleState.detached) {
  //     Provider.of<AppLockState>(context, listen: false)
  //         .lock(ModalRoute.of(context)!.settings.name!);
  //   }
  // }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<FacilityViewModel>(context);
    final machineViewModel = Provider.of<MachineViewModel>(context);
    final coreViewModel = Provider.of<CoreIssueViewModel>(context);
    return Scaffold(
      appBar: const Header(
        title: '작업장 선택',
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
                    const CheckBanner(word: '작업장', content: ' 위치를 선택 해주세요'),
                    const SizedBox(
                      height: 20,
                    ),
                    viewModel.facilities.isEmpty
                        ? const Center(
                            child: Text(
                              '작업 가능한 장비가 없습니다.',
                              style: TextStyle(fontSize: 20),
                            ),
                          )
                        : Expanded(
                            child: ListView.builder(
                            itemCount: viewModel.facilities.length,
                            itemBuilder: (context, index) {
                              return SelectList(
                                title: viewModel.facilities[index].facilityName,
                                onTap: () {
                                  coreViewModel.setFacilityName(
                                      viewModel.facilities[index].facilityName);
                                  machineViewModel.setFacilityId(
                                      viewModel.facilities[index].facilityId);
                                  Navigator.pushNamed(context, '/machine');
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
