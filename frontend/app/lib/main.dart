import 'package:flutter/material.dart';
import 'package:flutter_native_splash/flutter_native_splash.dart';
import 'package:flutter_screen_lock/flutter_screen_lock.dart';
import 'package:local_auth/local_auth.dart';

const Color blue100 = Color.fromRGBO(135, 185, 231, 1);
const Color blue800 = Color.fromRGBO(56, 61, 101, 1);
const Color mint200 = Color.fromRGBO(207, 241, 214, 1);
const Color samsungBlue = Color.fromRGBO(20, 40, 160, 1);

void main() {
  WidgetsBinding widgetsBinding =
      WidgetsFlutterBinding.ensureInitialized(); // 초기화 보장
  FlutterNativeSplash.preserve(widgetsBinding: widgetsBinding);
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
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  void initState() {
    super.initState();
    initialization();
  }

  void initialization() async {
    FlutterNativeSplash.remove();
  }

  Future<void> localAuth(BuildContext context) async {
    final localAuth = LocalAuthentication();
    final didAuthenticate = await localAuth.authenticate(
        localizedReason: 'Please authenticate',
        options: const AuthenticationOptions(biometricOnly: true));
    if (didAuthenticate) {
      Navigator.pop(context);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Padding(
          padding: const EdgeInsets.all(16),
          child: Center(
            child: ConstrainedBox(
              constraints: const BoxConstraints(
                maxWidth: 700,
              ),
              child: Wrap(
                spacing: 16,
                runSpacing: 16,
                alignment: WrapAlignment.start,
                children: [
                  ElevatedButton(
                    onPressed: () {
                      screenLockCreate(
                        context: context,
                        title: const Text('암호를 입력해주세요.'),
                        confirmTitle: const Text('암호를 다시 입력해주세요.'),

                        // 5번 틀리면 60초동안 입력 금지
                        maxRetries: 5,
                        retryDelay: const Duration(seconds: 60),
                        delayBuilder: (context, delay) => Text(
                          '${(delay.inMilliseconds / 1000).ceil()}초 뒤에 입력 가능합니다.',
                        ),

                        // 지문인식
                        customizedButtonChild: const Icon(
                          Icons.fingerprint,
                        ),
                        customizedButtonTap: () async =>
                            await localAuth(context),
                        onOpened: () async => await localAuth(context),

                        onConfirmed: (value) => Navigator.of(context).pop(),
                        config: const ScreenLockConfig(
                          backgroundColor: blue100,
                          // titleTextStyle: TextStyle(fontSize: 24),
                        ),
                        secretsConfig: SecretsConfig(
                          spacing: 15, // or spacingRatio
                          padding: const EdgeInsets.all(40),
                          secretConfig: SecretConfig(
                            disabledColor: Colors.grey,
                            enabledColor: blue100,
                            size: 15,
                            builder: (context, config, enabled) => Container(
                              decoration: BoxDecoration(
                                shape: BoxShape.circle,
                                color: enabled
                                    ? config.enabledColor
                                    : config.disabledColor,
                              ),
                              padding: const EdgeInsets.all(10),
                              width: config.size,
                              height: config.size,
                            ),
                          ),
                        ),
                        keyPadConfig: const KeyPadConfig(
                          // 길게 누르면 전체 삭제
                          clearOnLongPressed: true,
                        ),
                      );
                    },
                    child: const Text('잠금화면'),
                  ),
                ],
              ),
            ),
          ),
        ));
  }
}