import 'package:app/models/user/my_info_model.dart';
import 'package:app/services/user_service.dart';
import 'package:flutter/material.dart';

class MyInfoViewModel extends ChangeNotifier {
  final UserService _userService = UserService();

  MyInfoModel? _myInfoModel;
  bool _isLoading = false;
  String? _errorMessage;

  MyInfoModel? get myInfoModel => _myInfoModel;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  MyInfoViewModel() {
    myInfo();
  }

  Future<void> myInfo() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _userService.myInfo();
    _isLoading = false;

    if (!result['success']) {
      _errorMessage = result['message'];
    } else {
      _myInfoModel = result['myInfo'];
      _errorMessage = null;
    }
    notifyListeners();
  }
}
