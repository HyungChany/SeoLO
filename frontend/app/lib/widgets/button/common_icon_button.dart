import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../../main.dart';

// 사용법 예시

// import 'package:app/widgets/common_icon_button.dart';
// or
// CommonIconButton tab키 누르면 알아서 import

// CommonIconButton(
// text: 'NFC 태그',
// iconImage: 'assets/images/nfc_tag_icon.png',
// shape: BoxShape.rectangle,
// onTap: () {})

class CommonIconButton extends StatefulWidget {

  @override
  State<CommonIconButton> createState() => _CommonIconButtonState();
  // final => 더이상 변경되지 않는 값
  // 필수가 아니라면 뒤에 ?
  final String text; // 버튼 내용
  final String iconImage; // 아이콘 이미지 경로
  final BoxShape shape; // 버튼의 모양
  final VoidCallback onTap; // 클릭 이벤트
  final bool isSelected;

  // 필수가 아니라면 required 빼기
  const CommonIconButton({
    super.key,
    this.isSelected = false,
    required this.text,
    required this.iconImage,
    required this.shape,
    required this.onTap,
  });
}
class _CommonIconButtonState extends State<CommonIconButton>{
  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: widget.onTap,
      child: Column(
        // 세로로 세울 애들이 누구인지
        children: [
          // 아이콘이 있는 부분
          Container(
            height: widget.shape == BoxShape.rectangle ? MediaQuery.of(context).size.height * 0.13 : MediaQuery.of(context).size.height * 0.13,
            width: widget.shape == BoxShape.rectangle ? MediaQuery.of(context).size.width * 0.3 : MediaQuery.of(context).size.width * 0.3,
            alignment: Alignment.center,
            decoration: BoxDecoration(
                color: widget.shape == BoxShape.rectangle ? mint200 : gray100,
                shape: widget.shape,
                border:Border.all(
                  color: widget.isSelected ? samsungBlue : Colors.white,
                  width: 2
                ),
                borderRadius: widget.shape == BoxShape.rectangle
                    ? BorderRadius.circular(15.0)
                    : null,
                boxShadow: const [shadow],
                image: DecorationImage(
                  image: AssetImage(widget.iconImage),
                )),
          ),
          SizedBox(height: 10,),
          // 텍스트 부분
          Text(
            widget.text,
            textAlign: TextAlign.center,
            style: TextStyle(
              color: widget.isSelected ? samsungBlue : Colors.black,
              fontSize: 20,
              fontWeight: FontWeight.bold,
            ),
          )
        ],
      ),
    );
  }
}
