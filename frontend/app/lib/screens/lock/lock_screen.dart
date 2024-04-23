import 'package:app/main.dart';
import 'package:app/services/test_service.dart';
import 'package:app/widgets/common_icon_button.dart';
import 'package:app/widgets/common_text_button.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screen_lock/flutter_screen_lock.dart';
import 'package:local_auth/local_auth.dart';

class LockScreen extends StatefulWidget {
  const LockScreen({super.key});

  @override
  State<LockScreen> createState() => _LockScreenState();
}

class _LockScreenState extends State<LockScreen> {
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
              CommonIconButton(
                  text: 'NFC 태그',
                  iconImage: 'assets/images/nfc_tag_icon.png',
                  shape: BoxShape.rectangle,
                  onTap: () {
                    TestService testService = TestService();
                    testService.getTest();
                  }),
              CommonIconButton(
                  text: '정비',
                  iconImage: 'assets/images/maintenance_icon.png',
                  shape: BoxShape.circle,
                  onTap: () {}),
              CommonTextButton(text: '다음 단계', onTap: () {}),
              ElevatedButton(
                onPressed: () {
                  screenLockCreate(
                    context: context,
                    title: const Text('암호를 입력해주세요.'),
                    confirmTitle: const Text('암호를 다시 입력해주세요.'),
                    canCancel: false,
                    // // 5번 틀리면 60초동안 입력 금지
                    // maxRetries: 2,
                    // retryDelay: const Duration(seconds: 60),
                    // delayBuilder: (context, delay) => Text(
                    //   '${(delay.inMilliseconds / 1000).ceil()}초 뒤에 입력 가능합니다.',
                    // ),

                    // 지문인식
                    customizedButtonChild: const Icon(
                      Icons.fingerprint,
                    ),
                    customizedButtonTap: () async => await localAuth(context),
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
