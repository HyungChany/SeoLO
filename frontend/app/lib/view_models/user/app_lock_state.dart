import 'package:flutter/material.dart';

class AppLockState with ChangeNotifier {
  bool _isLocked = false;
  String _lastRoute = '/home';
  List<String> _history = [];

  bool get isLocked => _isLocked;
  String get lastRoute => _lastRoute;
  List<String> get history => _history;

  void lock(String currentRoute) {
    _isLocked = true;
    _lastRoute = currentRoute;
    _history.add(currentRoute);
    notifyListeners();
  }

  void unlock() {
    _isLocked = false;
    notifyListeners();
  }

  void popHistory() {
    _history.removeLast();
    _lastRoute = _history.isNotEmpty ? _history.last : '/main';
    notifyListeners();
  }
}
