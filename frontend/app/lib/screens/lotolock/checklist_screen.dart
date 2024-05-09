import 'package:app/main.dart';
import 'package:app/view_models/loto/checklist_view_model.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/checklist/check_banner.dart';
import 'package:app/widgets/button/common_text_button.dart';
import 'package:provider/provider.dart';

class CheckScreen extends StatefulWidget {
  const CheckScreen({super.key});

  @override
  State<CheckScreen> createState() => _CheckScreenState();
}

class _CheckScreenState extends State<CheckScreen> {
  @override
  void initState() {
    super.initState();
    Provider.of<ChecklistViewModel>(context, listen: false).loadInitialData();
  }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<ChecklistViewModel>(context);

    return Scaffold(
      appBar: const Header(
        title: '체크리스트',
        back: true,
      ),
      body: viewModel.isLoading
          ? const Center(
              child: CircularProgressIndicator(),
            )
          : Column(
            children: [
              const SizedBox(
                height: 20,
              ),
              const CheckBanner(
                word: '체크리스트',
                content: '의 모든 항목을 확인해주세요',
              ),
              const SizedBox(
                height: 20,
              ),
              Expanded(
                child: SingleChildScrollView(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: List.generate(
                      viewModel.checklist.length,
                      (index) => CheckboxListTile(
                        title: Text(viewModel.checklist[index].context),
                        value: viewModel.isCheckedList[index],
                        onChanged: (value) {
                          setState(() {
                            viewModel.isCheckedList[index] = value ?? false;
                          });
                        },
                        checkColor: snow,
                        activeColor: samsungBlue,
                      ),
                    ),
                  ),
                ),
              ),
              Container(
                height: MediaQuery.of(context).size.height * 0.1,
                alignment: Alignment.bottomCenter,
                child: Padding(
                  padding: const EdgeInsets.only(bottom: 20),
                  child: CommonTextButton(
                    text: '다음 단계',
                    onTap: viewModel.isCheckedList
                        .every((element) => element)
                        ? () {
                      Navigator.pushReplacementNamed(
                          context, '/worklist');
                    }
                        : () {
                      showDialog(
                          context: context,
                          barrierDismissible: true,
                          builder: (BuildContext context) {
                            return const CommonDialog(
                              content: '체크리스트의 모든 항목에 체크해 주세요.',
                              buttonText: '확인',
                            );
                          });
                    },
                  ),
                ),
              ),
            ],
          ),
    );
  }
}
