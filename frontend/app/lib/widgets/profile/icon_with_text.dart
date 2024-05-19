import 'package:flutter/material.dart';

class IconWithText extends StatelessWidget {
  final Icon icon;
  final String text;
  final String naviPage;

  const IconWithText({
    super.key,
    required this.icon,
    required this.text,
    required this.naviPage,
  });

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () {
        Navigator.pushNamed(context, naviPage);
      },
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 4.0),
        child: Row(
          children: [
            icon,
            const SizedBox(
              width: 10,
            ),
            Text(
              text,
              style:
                  const TextStyle(fontSize: 23.0, fontWeight: FontWeight.w400),
            ),
          ],
        ),
      ),
    );
  }
}
