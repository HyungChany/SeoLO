import 'package:app/main.dart';
import 'package:app/view_models/user/my_tasks_view_model.dart';
import 'package:app/widgets/card/common_card.dart';
import 'package:app/widgets/dialog/dialog.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class MyLoto extends StatefulWidget {
  const MyLoto({super.key});

  @override
  State<MyLoto> createState() => _MyLotoState();
}

class _MyLotoState extends State<MyLoto> {
  int currentIndex = 0;
  late PageController _pageController;

  @override
  void initState() {
    super.initState();
    _pageController = PageController(
      initialPage: currentIndex,
      viewportFraction: 0.38,
    );
    final viewModel = Provider.of<MyTasksViewModel>(context, listen: false);
    viewModel.myTasks().then((_) {
      if (viewModel.errorMessage == null) {
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
  }

  @override
  void dispose() {
    _pageController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<MyTasksViewModel>(context);
    return (viewModel.isLoading)
        ? SizedBox(
            height: MediaQuery.of(context).size.height * 0.4,
            child: Center(
              child: Container(
                width: MediaQuery.of(context).size.width * 0.35,
                height: MediaQuery.of(context).size.height * 0.05,
                decoration: BoxDecoration(
                  color: gray200,
                  borderRadius: BorderRadius.circular(20.0),
                ),
              ),
            ))
        : Column(
            children: [
              SizedBox(
                height: MediaQuery.of(context).size.height * 0.4,
                child: viewModel.myTasksModel!.isEmpty
                    ? const Center(
                        child: Text(
                          '작업 내역이 없습니다.',
                          style: TextStyle(fontSize: 20),
                        ),
                      )
                    : PageView.builder(
                        itemCount: viewModel.myTasksModel!.length,
                        controller: _pageController,
                        onPageChanged: (int index) {
                          setState(() {
                            currentIndex = index;
                          });
                        },
                        itemBuilder: (BuildContext context, int index) {
                          return _buildListItem(index);
                        },
                      ),
              ),
            ],
          );
  }

  Widget _buildListItem(int index) {
    final viewModel = Provider.of<MyTasksViewModel>(context);

    String? startTimeString = viewModel.myTasksModel![index].startTime ?? '';
    List<String> startParts = startTimeString.split('T');
    String formattedStartTime = (startTimeString == '')
        ? ''
        : '${startParts[0]} | ${startParts[1].substring(0, 5)}';

    String? endTimeString = viewModel.myTasksModel![index].endTime ?? '';
    List<String> endParts = endTimeString.split('T');
    String formattedEndTime = (endTimeString == '')
        ? ''
        : '${endParts[0]} | ${endParts[1].substring(0, 5)}';

    return GestureDetector(
      onTap: () {
        setState(() {
          currentIndex = index;
          _pageController.animateToPage(
            index,
            duration: const Duration(milliseconds: 500),
            curve: Curves.easeInOut,
          );
        });
      },
      child: Stack(
        children: [
          AnimatedContainer(
            duration: const Duration(milliseconds: 500),
            curve: Curves.easeInOut,
            transform: Matrix4.identity()
              ..translate(index == currentIndex
                  ? 0.0
                  : index < currentIndex
                      ? 20.0
                      : -20.0),
            child: Transform.scale(
              scale: index == currentIndex ? 1.0 : 0.75,
              child: CommonCard(
                facility: viewModel.myTasksModel![index].facilityName,
                machine: viewModel.myTasksModel![index].machineName,
                start: formattedStartTime,
                end: formattedEndTime,
                center: index == currentIndex ? true : false,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
