import 'package:app/main.dart';
import 'package:flutter/material.dart';

class LogoutButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width * 0.25,
      height: MediaQuery.of(context).size.height * 0.045,
      decoration: BoxDecoration(borderRadius: BorderRadius.circular(10.0),
          color: Color.fromRGBO(217, 217, 217, 1),
          boxShadow: [shadow]),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(Icons.logout),
          SizedBox(width: 5,),
          Text('로그아웃', style: TextStyle(fontWeight: FontWeight.bold),)
        ],
      ),
    );
  }
}