import 'package:app/routes/main_route.dart';
import 'package:app/screens/login/login_screen.dart';
import 'package:app/view_models/checklist/checklist_view_model.dart';
import 'package:app/view_models/main/news_view_model.dart';
import 'package:app/view_models/user/login_view_model.dart';
import 'package:app/view_models/user/my_info_view_model.dart';
import 'package:app/view_models/user/password_change_view_model.dart';
import 'package:app/view_models/user/password_check_view_model.dart';
import 'package:app/view_models/user/pin_change_view_model.dart';
import 'package:app/view_models/user/pin_login_view_model.dart';
import 'package:app/view_models/user/logout_view_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:provider/provider.dart';
import 'package:flutter_localizations/flutter_localizations.dart';

// main.dart import 후 color: blue100 이러한 방식으로 사용
// 참고로 color: blue100하고 tab 누르면 알아서 import 됨
const Color blue100 = Color.fromRGBO(135, 185, 231, 1);
const Color blue400 = Color.fromRGBO(52, 107, 190, 1);
const Color blue800 = Color.fromRGBO(56, 61, 101, 1);
const Color mint200 = Color.fromRGBO(207, 241, 214, 1);
const Color gray100 = Color.fromRGBO(242, 244, 247, 1);
const Color gray200 = Color.fromRGBO(228, 231, 236, 1);
const Color gray800 = Color.fromRGBO(29, 41, 57, 1);
const Color samsungBlue = Color.fromRGBO(20, 40, 160, 1);
const Color safetyBlue = Color.fromRGBO(0, 0, 255, 1);
const Color snow = Color.fromRGBO(255, 250, 250, 1);
const Color green400 = Color.fromRGBO(93, 210, 122, 1);
const Color red300 = Color.fromRGBO(241, 38, 13, 1);
const Color yellow200 = Color.fromRGBO(250, 237, 11, 1);
const Color safetyRed = Color.fromRGBO(255, 0, 0, 1);
// 그림자
const BoxShadow shadow = BoxShadow(
  color: Color.fromRGBO(0, 0, 0, 0.25), // 그림자 확산 범위
  blurRadius: 5, // 그림자 흐림 정도
  offset: Offset(0, 2), // 그림자 위치
);

void main() async {
  // WidgetsBinding widgetsBinding =
  WidgetsFlutterBinding.ensureInitialized(); // 초기화 보장
  // FlutterNativeSplash.preserve(widgetsBinding: widgetsBinding);
  await dotenv.load(fileName: '.env');
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MultiProvider(
        providers: [
          ChangeNotifierProvider(create: (_) => NewsViewModel()),
          ChangeNotifierProvider(create: (_) => LoginViewModel()),
          ChangeNotifierProvider(create: (_) => LogoutViewModel()),
          ChangeNotifierProvider(create: (_) => PinLoginViewModel()),
          ChangeNotifierProvider(create: (_) => PinChangeViewModel()),
          ChangeNotifierProvider(create: (_) => MyInfoViewModel()),
          ChangeNotifierProvider(create: (_) => PasswordChangeViewModel()),
          ChangeNotifierProvider(create: (_) => PasswordCheckViewModel()),
          ChangeNotifierProvider(create: (_) => ChecklistViewModel()),
        ],
        child: MaterialApp(
          localizationsDelegates: [
            GlobalMaterialLocalizations.delegate,
            GlobalCupertinoLocalizations.delegate,
          ],
          supportedLocales: const [
            Locale('en', ''), // English, no country code
            Locale('ko', ''), // Korean, no country code
          ],
          // supportedLocales: _localization.supportedLocales,
          // localizationsDelegates: _localization.localizationsDelegates,
          title: 'SeoLo',
          // debugShowCheckedModeBanner: false,
          theme: ThemeData(
              primarySwatch: Colors.blue,
              visualDensity: VisualDensity.adaptivePlatformDensity,
              fontFamily: 'font'),
          home: const LoginScreen(),
          onGenerateRoute: generateMainRoute,
        ));
  }
}
