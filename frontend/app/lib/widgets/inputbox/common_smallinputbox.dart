import 'package:app/main.dart';
import 'package:flutter/material.dart';

class SmallInputBox extends StatefulWidget {
  final String hintText;
  final TextInputAction textInputAction;
  final bool obscureText; // 비밀번호 안 보이게
  final ValueChanged<String> onChanged;

  // 생성자에서 hintText를 required 키워드와 함께 요구하여, 이 값을 반드시 제공해야 하도록 합니다.
  const SmallInputBox({
    super.key,
    required this.hintText,
    required this.textInputAction,
    this.obscureText = false,
    required this.onChanged,
  });

  @override
  State<SmallInputBox> createState() => _SmallInputBoxState(obscureText);
}

class _SmallInputBoxState extends State<SmallInputBox> {
  bool _isObscure = false;

  _SmallInputBoxState(bool obscureText) {
    _isObscure = obscureText; // 수정된 부분
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: MediaQuery.of(context).size.width * 0.82,
      height: MediaQuery.of(context).size.height * 0.06,
      child: TextFormField(
          scrollPadding:
              EdgeInsets.only(bottom: MediaQuery.of(context).viewInsets.bottom),
          onTapOutside: (event) =>
              FocusManager.instance.primaryFocus?.unfocus(),
          textAlignVertical: TextAlignVertical.center,
          textInputAction: widget.textInputAction,
          obscureText: _isObscure,
          onChanged: widget.onChanged,
          validator: (value) {
            if (value!.isEmpty) {
              return '내용을 입력해주세요';
            }
            return null;
          },
          decoration: InputDecoration(
            filled: true,
            fillColor: Colors.white,
            focusColor: blue400,
            border:
                OutlineInputBorder(borderRadius: BorderRadius.circular(8.0)),
            hintText: widget.hintText,
            // TextField의 hintText 속성에 생성자를 통해 받은 hintText를 사용
            hintStyle: const TextStyle(
                color: gray200, fontSize: 18.0, fontWeight: FontWeight.w400),
            suffixIcon: (widget.obscureText) ? IconButton(
              onPressed: () {
                setState(() {
                  _isObscure = !_isObscure;
                });
              },
              icon: _isObscure
                  ? Icon(Icons.visibility)
                  : Icon(Icons.visibility_off),
            ) : null,
          )),
    );
  }
}
