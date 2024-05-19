import 'package:app/widgets/button/common_text_button.dart';
import 'package:flutter/material.dart';

class CommonDialog extends StatelessWidget {
  final String? title;
  final String content;
  final String buttonText;
  final VoidCallback? buttonClick;

  const CommonDialog({
    super.key,
    this.title,
    required this.content,
    required this.buttonText,
    this.buttonClick,
  });

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(10.0),
      ),
      title: title != null ? Text(title!) : null,
      content: Text(content),
      actions: [
        CommonTextButton(
            onTap: buttonClick ?? () => Navigator.pop(context),
            text: buttonText,
            width: 80),
      ],
    );
  }
}
