import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';
import 'package:app/view_models/core/core_issue_view_model.dart';

class LargeInputBox extends StatefulWidget {
  final String hintText;
  final String? precaution;

  const LargeInputBox({Key? key, required this.hintText, this.precaution}) : super(key: key);

  @override
  State<LargeInputBox> createState() => _LargeInputBoxState();
}

class _LargeInputBoxState extends State<LargeInputBox> {
  final TextEditingController _controller = TextEditingController();
  final int _maxLength = 300;

  @override
  void initState() {
    super.initState();
    if (widget.precaution != null) {
      _controller.text = widget.precaution!;
    }
  }

  @override
  void didUpdateWidget(covariant LargeInputBox oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.precaution != null && widget.precaution != oldWidget.precaution) {
      _controller.text = widget.precaution!;
    }
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final coreViewModel = Provider.of<CoreIssueViewModel>(context);
    return SizedBox(
      width: MediaQuery.of(context).size.width * 0.8,
      height: MediaQuery.of(context).size.height * 0.4,
      child: Stack(
        alignment: Alignment.topRight,
        children: [
          TextField(
            onChanged: (value) {coreViewModel.setTaskPrecaution(value);},
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
