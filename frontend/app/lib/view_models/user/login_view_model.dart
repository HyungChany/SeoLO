import 'package:app/models/user/login_model.dart';
import 'package:app/services/user_service.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class LoginViewModel extends ChangeNotifier {
  final UserService _userService = UserService();

  LoginModel _loginData = LoginModel(
      username: '', password: '', companyCode: '');
  bool _isFocused = false;
  bool _isLoading = false;
  String? _errorMessage;
  int? _statusCode;

  int? get statusCode => _statusCode;
  String get userName => _loginData.username;

  String get password => _loginData.password;

  String get companyCode => _loginData.companyCode;

  bool get isFocused => _isFocused;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  void setUsername(String value) {
    _loginData = LoginModel(username: value,
        password: _loginData.password,
        companyCode: _loginData.companyCode);
    notifyListeners();
  }

  void setPassword(String value) {
    _loginData = LoginModel(username: _loginData.username,
        password: value,
        companyCode: _loginData.companyCode);
    notifyListeners();
  }

  void setCompanyCode(String value) {
    _loginData = LoginModel(username: _loginData.username,
        password: _loginData.companyCode,
        companyCode: value);
    notifyListeners();
  }

  void setFocused(bool value) {
    _isFocused = value;
    notifyListeners();
  }

  Future<void> login() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _userService.login(_loginData);
    _isLoading = false;

    if (!result['success']) {
      _errorMessage = result['message'];
      _statusCode = result['statusCode'];
    } else {
      _errorMessage = null;
      _statusCode = result['statusCode'];
    }
    notifyListeners();
  }
}