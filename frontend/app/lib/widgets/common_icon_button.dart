import 'package:flutter/material.dart';

import '../main.dart';

// 사용법 예시

// import 'package:app/widgets/common_icon_button.dart';
// or
// CommonIconButton tab키 누르면 알아서 import

// CommonIconButton(
// text: 'NFC 태그',
// iconImage: 'assets/images/nfc_tag_icon.png',
// shape: BoxShape.rectangle,
// onTap: () {})

class CommonIconButton extends StatelessWidget {
  // final => 더이상 변경되지 않는 값
  // 필수가 아니라면 뒤에 ?
  final String text; // 버튼 내용
  final String iconImage; // 아이콘 이미지 경로
  final BoxShape shape; // 버튼의 모양
  final VoidCallback onTap; // 클릭 이벤트

  // 필수가 아니라면 required 빼기
  const CommonIconButton({super.key,
    required this.text,
    required this.iconImage,
    required this.shape,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: onTap,
      child: Column(
        // 가운데 정렬
        mainAxisAlignment: MainAxisAlignment.center,
        // 세로로 세울 애들이 누구인지
        children: [
          // 아이콘이 있는 부분
          Container(
            height: shape == BoxShape.rectangle ? 120 : 100,
            width: shape == BoxShape.rectangle ? 120 : 100,
            alignment: Alignment.center,
            decoration: BoxDecoration(
                color: shape == BoxShape.rectangle ? mint200 : gray100,
                shape: shape,
                borderRadius: shape == BoxShape.rectangle ? BorderRadius.circular(15.0) : null,
                boxShadow: const [BoxShadow(
                    color: Color.fromRGBO(0, 0, 0, 0.25),
                    blurRadius: 4.0,
                    spreadRadius: 3.0,
                    offset: Offset(
                      3, 3,
                    )
                )
                ],
                image: DecorationImage(
                  image: AssetImage(iconImage),
                )
            ),
          ),
          // 텍스트 부분
          Text(text, textAlign: TextAlign.center,
            style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold,),)
        ],
      ),
    );
  }
}