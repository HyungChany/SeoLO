import 'package:app/services/user_service.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class UserViewModel extends ChangeNotifier {
  final _storage = FlutterSecureStorage();
  bool _isLoading = false;
  String? _errorMessage;
  final UserService _userService = UserService();

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;


  // 로그아웃
  Future<void> logout() async {
    await _userService.logout();
    notifyListeners();
  }
}
