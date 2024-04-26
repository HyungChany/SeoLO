import 'package:flutter/material.dart';
import 'package:app/widgets/checklist/check_banner.dart';
import 'package:app/widgets/checklist/select_list.dart';
import 'package:app/widgets/header/header.dart';

class WorkPlaceSelectScreen extends StatefulWidget {
  const WorkPlaceSelectScreen({super.key});

  @override
  State<WorkPlaceSelectScreen> createState() => _WorkPlaceSelectScreenState();
}

class _WorkPlaceSelectScreenState extends State<WorkPlaceSelectScreen> {
  @override
  Widget build(BuildContext context) {
    final List<String> workPlaceList = [
      '제 1공장',
      '제 2공장',
      '제 3공장',
      '제 4공장',
      '제 5공장',
      '제 6공장'
    ];
    return Scaffold(
      appBar: Header(
        title: '작업장 선택',
        back: true,
      ),
      body: Center(
        child: SizedBox(
          width: MediaQuery.of(context).size.width,
          child: Column(
            children: [
              CheckBanner(word: '작업장', content: ' 위치를 선택 해주세요'),
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
