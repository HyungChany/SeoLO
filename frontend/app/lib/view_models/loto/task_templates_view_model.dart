import 'package:app/models/loto/task_templates_model.dart';
import 'package:flutter/material.dart';
import 'package:app/services/loto_service.dart';

class TaskTemplatesViewModel extends ChangeNotifier {
  final LotoService _lotoService = LotoService();

  List<TaskTemplatesModel> _templates = [];
  bool _isLoading = false;
  String? _errorMessage;

  List<TaskTemplatesModel> get templates => _templates;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  Future<void> getTemplates() async {
    _templates = [];
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _lotoService.getTaskTemplates();
    if (!result['success']) {
      _isLoading = false;
      _errorMessage = result['message'];
    } else {
      _isLoading = false;
      _errorMessage = null;
      _templates = result['templates'];
    }
    notifyListeners();
  }
}
