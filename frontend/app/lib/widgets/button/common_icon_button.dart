import 'package:flutter/material.dart';

import '../../main.dart';

class CommonIconButton extends StatefulWidget {
  @override
  State<CommonIconButton> createState() => _CommonIconButtonState();
  final String text;
  final String iconImage;
  final BoxShape shape;
  final VoidCallback onTap;
  final bool isSelected;

  const CommonIconButton({
    super.key,
    this.isSelected = false,
    required this.text,
    required this.iconImage,
    required this.shape,
    required this.onTap,
  });
}

class _CommonIconButtonState extends State<CommonIconButton> {
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: widget.onTap,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Container(
            height: widget.shape == BoxShape.rectangle
                ? MediaQuery.of(context).size.height * 0.16
                : MediaQuery.of(context).size.height * 0.13,
            width: widget.shape == BoxShape.rectangle
                ? MediaQuery.of(context).size.width * 0.35
                : MediaQuery.of(context).size.width * 0.3,
            alignment: Alignment.center,
            decoration: BoxDecoration(
                color: widget.shape == BoxShape.rectangle
                    ? const Color.fromRGBO(229, 247, 232, 1)
                    : gray100,
                shape: widget.shape,
                border: Border.all(
                    color: widget.isSelected ? samsungBlue : Colors.transparent,
                    width: 2),
                borderRadius: widget.shape == BoxShape.rectangle
                    ? BorderRadius.circular(15.0)
                    : null,
                boxShadow: const [shadow],
                image: DecorationImage(
                  image: AssetImage(widget.iconImage),
                )),
          ),
          Text(
            widget.text,
            textAlign: TextAlign.center,
            style: TextStyle(
              color: widget.isSelected ? samsungBlue : Colors.black,
              fontSize: 18,
              fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(
            height: 5,
          )
        ],
      ),
    );
  }
}
