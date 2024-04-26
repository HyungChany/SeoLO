import 'package:app/screens/lock/lock_screen.dart';
import 'package:app/screens/main/main_screen.dart';
import 'package:app/screens/test_screen.dart';
import 'package:flutter/material.dart';
import 'package:app/screens/lotolock/checklist_screen.dart';
import 'package:app/screens/lotolock/workplace_select_screen.dart';
import 'package:app/screens/lotolock/facility_select_screen.dart';
import 'package:app/screens/lotolock/worklist_select_screen.dart';
Route<dynamic> generateMainRoute(RouteSettings settings) {
  switch (settings.name) {
    case '/lock':
      return MaterialPageRoute(builder: (context) => LockScreen());

    case '/test':
      return MaterialPageRoute(builder: (context) => const TestScreen());

    case '/main':
      return MaterialPageRoute(builder: (context) => const MainScreen());
    case '/checklist':
      return MaterialPageRoute(builder: (context) => CheckScreen());
    case '/workplace':
      return MaterialPageRoute(builder: (context)=> const WorkPlaceSelectScreen());
    case '/facility':
      return MaterialPageRoute(builder: (context) => const FacilitySelectScreen());
    case '/worklist':
      return MaterialPageRoute(builder: (context)=> const WorkListSelectScreen());
    default:
      debugPrint('Route Error');
      return MaterialPageRoute(builder: (context) => const TestScreen());
  }
}