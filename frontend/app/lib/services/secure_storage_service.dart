import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class SecureStorageService {
  static final _storage = FlutterSecureStorage();

  static Future<String?> getToken() async {
    return await _storage.read(key: 'token');
  }

  static Future<void> setLastScreen(String screenName) async {
    await _storage.write(key: 'lastScreen', value: screenName);
  }

  static Future<String?> getLastScreen() async {
    return await _storage.read(key: 'lastScreen');
  }
}
