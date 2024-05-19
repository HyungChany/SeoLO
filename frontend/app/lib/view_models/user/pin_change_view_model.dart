import 'package:app/models/user/pin_change_model.dart';
import 'package:app/services/user_service.dart';
import 'package:flutter/material.dart';

class PinChangeViewModel extends ChangeNotifier {
  final UserService _userService = UserService();

  PinChangeModel _pinChangeData = PinChangeModel(newPin: '', checkNewPin: '');
  bool _isLoading = false;
  String? _errorMessage;

  String get newPin => _pinChangeData.newPin;

  String get checkNewPin => _pinChangeData.checkNewPin;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  void setNewPin(String value) {
    _pinChangeData = PinChangeModel(newPin: value, checkNewPin: '');
    notifyListeners();
  }

  void setCheckNewPin(String value) {
    _pinChangeData =
        PinChangeModel(newPin: _pinChangeData.newPin, checkNewPin: value);
  }

  Future<void> pinChange() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _userService.pinChange(_pinChangeData);
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
