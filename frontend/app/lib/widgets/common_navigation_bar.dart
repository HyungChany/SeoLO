import 'package:flutter/material.dart';
import '../main.dart';

class CustomBottomNavigationBar extends StatelessWidget {
  final int selectedIndex;
  final Function(int) onItemTapped;

  const CustomBottomNavigationBar({
    super.key,
    required this.selectedIndex,
    required this.onItemTapped,
  });

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      items: <BottomNavigationBarItem>[
        BottomNavigationBarItem(
          icon: Image.asset('assets/images/home_icon.png',width: 34,height: 34),
          label: 'Home',
        ),
        BottomNavigationBarItem(
          icon: Image.asset('assets/images/nfc_icon.png',width: 34,height: 34),
          label: 'NFC',
        ),
        BottomNavigationBarItem(
          icon: Image.asset('assets/images/profile_icon.png',width: 34,height: 34),
          label: 'Profile',
        ),
      ],
      currentIndex: selectedIndex,
      selectedItemColor: samsungBlue,
      onTap: onItemTapped,
    );
  }
}
