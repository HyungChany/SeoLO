import 'package:app/screens/bluetooth/bluetooth_off_screen.dart';
import 'package:app/screens/bluetooth/bluetooth_screen.dart';
import 'package:app/screens/login/login_screen.dart';
import 'package:app/screens/login/pin_login_screen.dart';
import 'package:app/screens/loto_process/loto_process_screen.dart';
import 'package:app/screens/lotolock/result_lock_screen.dart';
import 'package:app/screens/lotolock/result_unlock_screen.dart';
import 'package:app/screens/main/main_screen.dart';
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
      return MaterialPageRoute(builder: (context) => const PinLoginScreen());
    case '/test':
      return MaterialPageRoute(builder: (context) => const TestScreen());
    case '/main':
      return MaterialPageRoute(builder: (context) => const MainScreen());
    case '/checklist':
      return MaterialPageRoute(builder: (context) => const ChecklistScreen());
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
      return MaterialPageRoute(builder: (context) => const ProfileScreen());
    case '/changePassword':
      return MaterialPageRoute(builder: (context) => const ChangePassword());
    case '/checkPassword':
      return MaterialPageRoute(builder: (context) => const CheckPassword());
    case '/checkPin':
      return MaterialPageRoute(builder: (context) => const CheckPinScreen());
    case '/changePin':
      return MaterialPageRoute(builder: (context) => const ChangePinScreen());
    case '/changePinCheck':
      return MaterialPageRoute(
          builder: (context) => const ChangePinCheckScreen());
    case '/bluetooth':
      return MaterialPageRoute(builder: (context) => const BluetoothScreen());
    case '/bluetoothOff':
      return MaterialPageRoute(
          builder: (context) => const BluetoothOffScreen());
    case '/selectDay':
      return MaterialPageRoute(builder: (context) => const DaySelect());
    case '/selectTime':
      return MaterialPageRoute(builder: (context) => const TimeSelect());
    case '/worklistCheck':
      return MaterialPageRoute(
          builder: (context) => const WorkListCheckScreen());
    case '/otherWorklistCheck':
      return MaterialPageRoute(
          builder: (context) => const OtherWorkListCheckScreen());
    case '/login':
      return MaterialPageRoute(builder: (context) => const LoginScreen());
    case '/lotoProcess':
      return MaterialPageRoute(builder: (context) => const LotoProcessScreen());
    case '/resultLock':
      return MaterialPageRoute(builder: (context) => const ResultLockScreen());
    case '/resultUnlock':
      return MaterialPageRoute(
          builder: (context) => const ResultUnlockScreen());
    default:
      return MaterialPageRoute(builder: (context) => const MainScreen());
  }
}
