import 'package:flutter/material.dart';
import 'package:local_auth/local_auth.dart';
import 'package:local_auth_android/local_auth_android.dart';

class FingerprintAuth {
  static final _auth = LocalAuthentication();

  static Future<bool> hasBiometrics() async {
    try {
      return await _auth.canCheckBiometrics ?? false;
    } catch (e) {
      debugPrint('에러 : $e');
    }
    return false;
  }

  static Future<List<BiometricType>> getBiometrics() async {
    try {
      return await _auth.getAvailableBiometrics();
    } catch (e) {
      debugPrint('에러 : $e');
    }
    return <BiometricType>[];
  }

  static Future<bool> authenticate() async {
    final isAvailable = await hasBiometrics();
    if (!isAvailable) return false;
    try {
      return await _auth.authenticate(
          localizedReason: '생체정보를 인식해주세요.',
          options: const AuthenticationOptions(
            biometricOnly: true,
            useErrorDialogs: true, //기본 대화 상자를 사용하기
            stickyAuth: true,
          ),
          authMessages: [
            const AndroidAuthMessages(
              biometricHint: '생체 정보를 스캔하세요.',
              biometricNotRecognized: '생체정보가 일치하지 않습니다.',
              biometricRequiredTitle: '지문인식',
              biometricSuccess: '로그인',
              cancelButton: '취소',
              deviceCredentialsRequiredTitle: '생체인식이 필요합니다.',
              deviceCredentialsSetupDescription: '기기 설정으로 이동하여 생체 인식을 등록하세요.',
              goToSettingsButton: '설정',
              goToSettingsDescription: '기기 설정으로 이동하여 생체 인식을 등록하세요.',
              signInTitle: '계속하려면 생체 인식을 스캔',
            )
          ]);
    } catch (e) {
      debugPrint('에러 : $e');
    }
    return false;
  }
}