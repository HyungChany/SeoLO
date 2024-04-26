import 'package:app/widgets/header/header.dart';
import 'package:app/widgets/profile/logout_button.dart';
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
      appBar:Header(title: '프로필', back: false),
      body: Row(
        children: [LogoutButton()
        ],
      ),
    );
  }
}