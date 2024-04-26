import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class LargeInputBox extends StatefulWidget {
  final String hintText;

  const LargeInputBox({Key? key, required this.hintText}) : super(key: key);

  @override
  _LargeInputBoxState createState() => _LargeInputBoxState();
}

class _LargeInputBoxState extends State<LargeInputBox> {
  final TextEditingController _controller = TextEditingController();
  final int _maxLength = 300;

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: MediaQuery.of(context).size.width * 0.8,
      height: MediaQuery.of(context).size.height * 0.4,
      child: Stack(
        alignment: Alignment.topRight,
        children: [
          TextField(
            controller: _controller,
            keyboardType: TextInputType.multiline,
            maxLines: 10,
            maxLength: _maxLength,
            buildCounter: (
                BuildContext context, {
                  required int currentLength,
                  required bool isFocused,
                  required int? maxLength,
                }) {
              return Text(
                '$currentLength / $maxLength',
                style: TextStyle(fontSize: 12),
              );
            },
            decoration: InputDecoration(
              labelText: '비고',
              border: OutlineInputBorder(),
              floatingLabelBehavior: FloatingLabelBehavior.always,
              hintText: widget.hintText,
            ),
            inputFormatters: [
              LengthLimitingTextInputFormatter(_maxLength),
            ],
          ),
        ],
      ),
    );
  }
}
