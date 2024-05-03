import 'package:app/models/user/pin_login_model.dart';
import 'package:app/services/user_service.dart';
import 'package:flutter/material.dart';

class PinLoginViewModel extends ChangeNotifier {
  final UserService _userService = UserService();

  PinLoginModel _pinLoginData = PinLoginModel(pin: '');
  bool _isLoading = false;
  String? _errorMessage;
  int? _failCount;
  String? _errorCode;

  String get pin  => _pinLoginData.pin;
  bool get isLoading => _isLoading;
  String? get errorMessage => _errorMessage;
  int? get failCount => _failCount;
  String? get errorCode => _errorCode;

  void setPin(String value) {
    _pinLoginData = PinLoginModel(pin: value);
    notifyListeners();
  }

  Future<void> pinLogin() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _userService.pinLogin(_pinLoginData);
    _isLoading = false;

    if (!result['success']) {
      _errorMessage = result['message'];
      _failCount = result['fail_count'];
      _errorCode = result['errorCode'];
    } else {
      _errorMessage = null;
    }
    notifyListeners();
  }
}