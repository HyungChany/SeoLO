import 'package:app/widgets/header/header.dart';
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
      appBar: Header(title: '프로필', back: false),
      body: Column(
        children: [
          SizedBox(height: 20,),
          Container(
            height: MediaQuery.of(context).size.height * 0.1,
            child: Row(
              children: [MyInfo(), LogoutButton()],
            ),
          ),
          Divider(color: Colors.grey, thickness: 1.0,)
        ],
      ),
    );
  }
}
