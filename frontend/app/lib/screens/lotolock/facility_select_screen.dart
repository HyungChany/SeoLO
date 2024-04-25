import 'package:flutter/material.dart';
import 'package:app/widgets/checklist/check_banner.dart';
import 'package:app/widgets/checklist/select_list.dart';
import 'package:app/widgets/header/header.dart';

class FacilitySelectScreen extends StatefulWidget {
  const FacilitySelectScreen({super.key});

  @override
  State<FacilitySelectScreen> createState() => _FacilitySelectScreenState();
}

class _FacilitySelectScreenState extends State<FacilitySelectScreen> {
  @override
  Widget build(BuildContext context) {
    final List<String> workPlaceList = [
      'F/B - 1',
      'F/B - 2',
      'L/A - 1',
      'L/W - 1',
      'L/W -2',
    ];
    return Scaffold(
      appBar: Header(
        title: '설비 선택',
        back: true,
      ),
      body: Center(
        child: SizedBox(
          width: MediaQuery.of(context).size.width,
          child: Column(
            children: [
              CheckBanner(word: '설비', content: '를 선택 해주세요'),
              SizedBox(
                height: 20,
              ),
              Expanded(
                  child: ListView.builder(
                    itemCount: workPlaceList.length,
                    itemBuilder: (context, index) {
                      return SelectList(title: workPlaceList[index]);
                    },
                  ))
            ],
          ),
        ),
      ),
    );
  }
}
