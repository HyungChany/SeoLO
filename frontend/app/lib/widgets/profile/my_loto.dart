import 'package:app/widgets/card/common_card.dart';
import 'package:flutter/material.dart';

class MyLoto extends StatefulWidget {
  @override
  _MyLotoState createState() => _MyLotoState();
}

class _MyLotoState extends State<MyLoto> {
  final List<String> facilities = ['1공장', '2공장', '3공장', '4공장'];
  final List<String> machines = ['장비1', '장비2', '장비3', '장비4'];
  final List<String> starts = ['1시', '2시', '3시', '4시'];
  final List<String> ends = ['5시', '6시', '7시', '8시'];
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
    return Column(
      children: [
        SizedBox(
          height: MediaQuery.of(context).size.height * 0.4,
          child: PageView.builder(
            itemCount: facilities.length,
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
    return GestureDetector(
      onTap: () {
        setState(() {
          currentIndex = index;
          _pageController.animateToPage(
            index,
            duration: Duration(milliseconds: 500),
            curve: Curves.easeInOut,
          );
        });
      },
      child: Stack(
        children: [
          AnimatedContainer(
            duration: Duration(milliseconds: 500),
            curve: Curves.easeInOut,
            transform: Matrix4.identity()..translate(index == currentIndex ? 0.0 : index < currentIndex ? 20.0 : -20.0),
            child: Transform.scale(
              scale: index == currentIndex ? 1.0 : 0.75,
              child: CommonCard(
                facility: facilities[index],
                machine: machines[index],
                start: starts[index],
                end: ends[index],
                center: index == currentIndex ? true : false,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
