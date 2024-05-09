import 'package:app/models/user/my_tasks_model.dart';
import 'package:app/services/user_service.dart';
import 'package:flutter/material.dart';

class MyTasksViewModel extends ChangeNotifier {
  final UserService _userService = UserService();

  MyTasksViewModel() {
    myTasks();
    notifyListeners();
  }

  List<MyTasksModel>? _myTasksModel;
  bool _isLoading = false;
  String? _errorMessage;

  List<MyTasksModel>? get myTasksModel => _myTasksModel;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  Future<void> myTasks() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _userService.myTasks();
    _isLoading = false;

    if (!result['success']) {
      _errorMessage = result['message'];
    } else {
      _myTasksModel = result['tasks'];
      _errorMessage = null;
    }
    notifyListeners();
  }
}
