import 'package:app/view_models/loto/facility_view_model.dart';
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

class _FacilitySelectScreenState extends State<FacilitySelectScreen> {
  @override
  void initState() {
    super.initState();
    Provider.of<FacilityViewModel>(context, listen: false).loadInitialData();
  }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<FacilityViewModel>(context);
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
                    Expanded(
                        child: ListView.builder(
                      itemCount: viewModel.facilities.length,
                      itemBuilder: (context, index) {
                        return SelectList(
                          title: viewModel.facilities[index].facilityName,
                          onTap: () {
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
