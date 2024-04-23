import 'package:app/screens/lock/lock_screen.dart';
import 'package:app/screens/test_screen.dart';
import 'package:flutter/material.dart';

Route<dynamic> generateMainRoute(RouteSettings settings) {
  switch (settings.name) {
    case '/lock':
      return MaterialPageRoute(builder: (context) => LockScreen());

    case '/test':
      return MaterialPageRoute(builder: (context) => const TestScreen());

    default:
      debugPrint('Route Error');
      return MaterialPageRoute(builder: (context) => const TestScreen());
  }
}