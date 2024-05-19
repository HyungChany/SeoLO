import 'package:app/services/user_service.dart';
import 'package:flutter/material.dart';

class LogoutViewModel extends ChangeNotifier {
  String? _errorMessage;
  final UserService _userService = UserService();

  String? get errorMessage => _errorMessage;

  // 로그아웃
  Future<void> logout() async {
    final result = await _userService.logout();

    if (!result['success']) {
      _errorMessage = result['message'];
    } else {
      _errorMessage = null;
    }
    notifyListeners();
  }
}
