import 'package:app/main.dart';
import 'package:flutter/material.dart';
import 'package:app/widgets/card/row_content.dart';

class CommonCard extends StatefulWidget {
  final String facility;
  final String machine;
  final String start;
  final String end;

  const CommonCard(
      {super.key,
      required this.facility,
      required this.machine,
      required this.start,
      required this.end});

  @override
  _CommonCardState createState() => _CommonCardState();
}

class _CommonCardState extends State<CommonCard> {
  late List<Map<String, String>> data = [
    {'title': '작업장', 'content': widget.facility},
    {'title': '설비명', 'content': widget.machine},
    {'title': '시작시간', 'content': widget.start},
    {'title': '종료시간', 'content': widget.end},
  ];

  lotoCard() {
    return Container(
      width: MediaQuery.of(context).size.width * 0.35,
      height: MediaQuery.of(context).size.height * 0.23,
      decoration: BoxDecoration(
        color: const Color.fromRGBO(237, 244, 251, 1),
        borderRadius: BorderRadius.circular(10.0),
        boxShadow: const [shadow],
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: data.map((item) {
          return Padding(
            padding: const EdgeInsets.symmetric(vertical: 4.0),
            child: RowContent(
              title: item['title']!,
              content: item['content']!,
              line: 2,
            ),
          );
        }).toList(),
      ),
    );
  }

  pressLotoCard() {
    return Container(
      decoration: BoxDecoration(
        color: const Color.fromRGBO(237, 244, 251, 1),
        borderRadius: BorderRadius.circular(10.0),
        boxShadow: const [shadow],
      ),
      child: Column(
        // mainAxisAlignment: MainAxisAlignment.start,
        // mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: data.map((item) {
          return Padding(
              padding: const EdgeInsets.symmetric(vertical: 4.0),
              child: RowContent(
                title: item['title']!,
                content: item['content']!,
                line: 5,
              ));
        }).toList(),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    OverlayEntry? overlayEntry;

    return GestureDetector(
        onLongPress: () {
          overlayEntry = OverlayEntry(
            builder: (context) => Center(
              child: Material(
                color: Colors.transparent,
                child: SizedBox(
                  width: MediaQuery.of(context).size.width * 0.5,
                  height: MediaQuery.of(context).size.height * 0.4,
                  child: pressLotoCard(),
                ),
              ),
            ),
          );
          Overlay.of(context).insert(overlayEntry!);
        },
        onLongPressEnd: (details) {
          overlayEntry?.remove();
        },
        child: lotoCard());
  }
}
