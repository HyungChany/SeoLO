import 'package:app/view_models/user/my_tasks_view_model.dart';
import 'package:app/widgets/card/common_card.dart';
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
  }

  @override
  void dispose() {
    _pageController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<MyTasksViewModel>(context);
    return viewModel.myTasksModel == []
        ? const Center(child: Text('작성된 LOTO가 없습니다.'))
        : viewModel.isLoading
            ? const Center(
                child: CircularProgressIndicator(),
              )
            : Column(
                children: [
                  SizedBox(
                    height: MediaQuery.of(context).size.height * 0.4,
                    child: viewModel.myTasksModel!.isEmpty
                        ? const Center(
                            child: Text('작업 내역이 없습니다.', style: TextStyle(fontSize: 20),),
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
                start: viewModel.myTasksModel![index].startTime ?? '시작 전',
                end: viewModel.myTasksModel![index].endTime ?? '종료 전',
                center: index == currentIndex ? true : false,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
