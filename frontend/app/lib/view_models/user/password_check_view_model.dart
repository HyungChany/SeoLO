import 'package:app/models/user/password_check_model.dart';
import 'package:app/services/user_service.dart';
import 'package:flutter/material.dart';

class PasswordCheckViewModel extends ChangeNotifier {
  final UserService _userService = UserService();

  PasswordCheckModel _passwordCheckData = PasswordCheckModel(nowPwd: '');
  bool _isLoading = false;
  String? _errorMessage;

  String get nowPwd => _passwordCheckData.nowPwd;
  bool get isLoading => _isLoading;
  String? get errorMessage => _errorMessage;

  void setNowPwd(String value) {
    _passwordCheckData = PasswordCheckModel(nowPwd: value);
    notifyListeners();
  }

  Future<void> passwordCheck() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _userService.passwordCheck(_passwordCheckData);
    _isLoading = false;

    if (!result['success']) {
      _errorMessage = result['message'];
    } else {
      _errorMessage = null;
    }
    notifyListeners();
  }
}