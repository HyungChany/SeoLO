import 'package:app/widgets/main/welcome_banner.dart';
import 'package:flutter/material.dart';

class MainScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Container(
          width: MediaQuery.of(context).size.width * 0.85,
          child: Column(
            children: [
              WelcomeBanner(),
            ],
          ),
        ),
      ),
    );
  }
}
