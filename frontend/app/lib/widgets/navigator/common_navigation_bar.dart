import 'package:flutter/material.dart';
import '../../main.dart';

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
      items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(
          icon: Icon(Icons.home_outlined),
          label: 'Home',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.bluetooth),
          label: 'BLUETOOTH',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.person_2_outlined),
          label: 'Profile',
        ),
      ],
      currentIndex: selectedIndex,
      selectedItemColor: samsungBlue,
      selectedLabelStyle: const TextStyle(fontWeight: FontWeight.bold),
      onTap: onItemTapped,
    );
  }
}
