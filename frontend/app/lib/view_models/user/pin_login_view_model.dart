import 'package:app/models/user/pin_login_model.dart';
import 'package:app/services/user_service.dart';
import 'package:flutter/material.dart';

class PinLoginViewModel extends ChangeNotifier {
  final UserService _userService = UserService();

  PinLoginModel _pinLoginData = PinLoginModel(pin: '');
  bool _isLoading = false;
  String? _errorMessage;

  String get pin  => _pinLoginData.pin;
  bool get isLoading => _isLoading;
  String? get errorMessage => _errorMessage;

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
      debugPrint('실패했을 때 메시지 : ${result['message']}');
      _errorMessage = result['message'];
    } else {
      _errorMessage = null;
    }
    notifyListeners();
  }
}