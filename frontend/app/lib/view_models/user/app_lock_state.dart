import 'package:flutter/material.dart';

class AppLockState with ChangeNotifier {
  bool _isLocked = false;
  String _lastRoute = '/main';
  List<String> _history = [];

  bool get isLocked => _isLocked;
  String get lastRoute => _lastRoute;
  List<String> get history => List.unmodifiable(_history);

  void lock() {
    _isLocked = true;
    // _lastRoute = currentRoute;
    // _history.add(currentRoute);
    notifyListeners();
  }

  void unlock() {
    _isLocked = false;
    notifyListeners();
  }

  void pushRoute(String route) {
    _history.add(route);
  }

  void popRoute() {
    if (_history.isNotEmpty) {
      _history.removeLast();
    }
    // _history.removeLast();
    // _lastRoute = _history.isNotEmpty ? _history.last : '/main';
    // notifyListeners();
  }

  void resetHistory(String route) {
    _history.clear();
    _history.add(route);
    notifyListeners();
  }

  void clearHistory() {
    _history.clear();
  }
}
