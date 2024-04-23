import 'package:app/routes/main_route.dart';
import 'package:app/screens/lock/lock_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_native_splash/flutter_native_splash.dart';

const Color blue100 = Color.fromRGBO(135, 185, 231, 1);
const Color blue800 = Color.fromRGBO(56, 61, 101, 1);
const Color mint200 = Color.fromRGBO(207, 241, 214, 1);
const Color gray100 = Color.fromRGBO(242, 244, 247, 1);
const Color samsungBlue = Color.fromRGBO(20, 40, 160, 1);

void main() async{
  // WidgetsBinding widgetsBinding =
      WidgetsFlutterBinding.ensureInitialized(); // 초기화 보장
  // FlutterNativeSplash.preserve(widgetsBinding: widgetsBinding);
      await dotenv.load(fileName: '.env');
      runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SeoLo',
      // debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.blue,
          visualDensity: VisualDensity.adaptivePlatformDensity
      ),
      home: const LockScreen(),
      onGenerateRoute: generateMainRoute,
    );
  }
}