import 'package:app/screens/bluetooth/bluetooth_screen.dart';
import 'package:app/screens/loading/loading_screen.dart';
import 'package:app/screens/login/login_screen.dart';
import 'package:app/screens/login/pin_login_screen.dart';
import 'package:app/screens/loto_process/loto_process_screen.dart';
import 'package:app/screens/main/main_screen.dart';
import 'package:app/screens/nfc/nfc_screen.dart';
import 'package:app/screens/profile/change_password_screen.dart';
import 'package:app/screens/profile/change_pin_check_screen.dart';
import 'package:app/screens/profile/change_pin_screen.dart';
import 'package:app/screens/profile/check_password_screen.dart';
import 'package:app/screens/profile/check_pin_screen.dart';
import 'package:app/screens/profile/profile_screen.dart';
import 'package:app/screens/test_screen.dart';
import 'package:flutter/material.dart';
import 'package:app/screens/lotolock/checklist_screen.dart';
import 'package:app/screens/lotolock/facility_select_screen.dart';
import 'package:app/screens/lotolock/machine_select_screen.dart';
import 'package:app/screens/lotolock/task_template_select_screen.dart';
import 'package:app/screens/lotolock/day_select_screen.dart';
import 'package:app/screens/lotolock/time_select_screen.dart';
import 'package:app/screens/lotolock/worklist_check_screen.dart';
import 'package:app/screens/lotolock/other_worklist_check_screen.dart';

Route<dynamic> generateMainRoute(RouteSettings settings) {
  switch (settings.name) {
    case '/pinLogin':
      return MaterialPageRoute(builder: (context) => PinLoginScreen());
    case '/test':
      return MaterialPageRoute(builder: (context) => const TestScreen());
    case '/main':
      return MaterialPageRoute(builder: (context) => const MainScreen());
    case '/checklist':
      return MaterialPageRoute(builder: (context) => CheckScreen());
    case '/facility':
      return MaterialPageRoute(
          builder: (context) => const FacilitySelectScreen());
    case '/machine':
      return MaterialPageRoute(
          builder: (context) => const MachineSelectScreen());
    case '/taskTemplate':
      return MaterialPageRoute(
          builder: (context) => const TaskTemplateSelectScreen());
    case '/profile':
      return MaterialPageRoute(builder: (context) => ProfileScreen());
    case '/changePassword':
      return MaterialPageRoute(builder: (context) => ChangePassword());
    case '/checkPassword':
      return MaterialPageRoute(builder: (context) => CheckPassword());
    case '/checkPin':
      return MaterialPageRoute(builder: (context) => CheckPinScreen());
    case '/changePin':
      return MaterialPageRoute(builder: (context) => ChangePinScreen());
    case '/changePinCheck':
      return MaterialPageRoute(builder: (context) => ChangePinCheckScreen());
    case '/nfc':
      return MaterialPageRoute(builder: (context) => NfcScreen());
    case '/bluetooth':
      // return MaterialPageRoute(builder: (context) => BluetoothScreen());
    case '/selectDay':
      return MaterialPageRoute(builder: (context) => DaySelect());
    case '/selectTime':
      return MaterialPageRoute(builder: (context) => TimeSelect());
    case '/worklistcheck':
      return MaterialPageRoute(builder: (context) => WorkListCheckScreen());
    case '/otherworklistcheck':
      return MaterialPageRoute(
          builder: (context) => OtherWorkListCheckScreen());
    case '/login':
      return MaterialPageRoute(builder: (context) => LoginScreen());
    case '/loading':
      return MaterialPageRoute(builder: (context) => LoadingScreen());
    case '/lotoProcess':
      return MaterialPageRoute(builder: (context) => LotoProcessScreen());
    default:
      debugPrint('Route Error');
      return MaterialPageRoute(builder: (context) => const TestScreen());
  }
}
