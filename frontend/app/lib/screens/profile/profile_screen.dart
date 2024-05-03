import 'package:app/widgets/card/common_card.dart';
import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/profile/icon_with_text.dart';
import 'package:app/widgets/profile/logout_button.dart';
import 'package:app/widgets/profile/my_info.dart';
import 'package:flutter/material.dart';

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({super.key});

  @override
  _ProfileScreenState createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const Header(title: '프로필', back: false),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const SizedBox(
            height: 20,
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              children: [const MyInfo(), LogoutButton()],
            ),
          ),
          const Divider(
            color: Colors.grey,
            thickness: 1.0,
          ),
          const Padding(
            padding: EdgeInsets.only(left: 20.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  '내 활동',
                  style: TextStyle(fontWeight: FontWeight.bold, fontSize: 25),
                ),
                SizedBox(height: 10,),
                IconWithText(icon: Icon(Icons.edit_document, size: 35,), text: '비밀번호 재설정', naviPage: '/changePassword',),
                IconWithText(icon: Icon(Icons.password, size: 35,), text: 'PIN 번호 재설정', naviPage: '/checkPin',)
              ],
            ),
          ),
          const Divider(color: Colors.grey, thickness: 1.0,),
          const Padding(
            padding: EdgeInsets.only(left: 20.0),
            child: Text('나의 LOTO', style: TextStyle(fontWeight: FontWeight.bold, fontSize: 25),),
          ),
          const CommonCard(facility: '검사', machine: '장비sssssssssssssssssssssssssssssssssssssss', start: '24.04.24 11:30', end: '24.04.24 11:30'),
        ],
      ),
    );
  }
}
