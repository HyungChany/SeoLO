import 'package:app/models/user/password_change_model.dart';
import 'package:app/services/user_service.dart';
import 'package:flutter/material.dart';

class PasswordChangeViewModel extends ChangeNotifier {
  final UserService _userService = UserService();

  PasswordChangeModel _passwordChangeData =
      PasswordChangeModel(newPwd: '', checkNewPwd: '');
  bool _isLoading = false;
  String? _errorMessage;

  String get newPwd => _passwordChangeData.newPwd;

  String get checkNewPwd => _passwordChangeData.checkNewPwd;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  void setNewPwd(String value) {
    _passwordChangeData = PasswordChangeModel(
        newPwd: value, checkNewPwd: _passwordChangeData.checkNewPwd);
    notifyListeners();
  }

  void setCheckNewPwd(String value) {
    _passwordChangeData = PasswordChangeModel(
        newPwd: _passwordChangeData.newPwd, checkNewPwd: value);
    notifyListeners();
  }

  Future<void> passwordChange() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _userService.passwordChange(_passwordChangeData);
    _isLoading = false;

    if (!result['success']) {
      _errorMessage = result['message'];
    } else {
      _errorMessage = null;
    }
    notifyListeners();
  }
}
