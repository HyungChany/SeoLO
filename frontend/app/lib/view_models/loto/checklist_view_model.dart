import 'package:flutter/material.dart';
import 'package:app/services/loto_service.dart';
import 'package:app/models/loto/checklist_model.dart';

class ChecklistViewModel extends ChangeNotifier{
  final LotoService _lotoService = LotoService();

  List <ChecklistModel> _checklist = [];
  List <bool> _isCheckedList = [];
  
  bool _isLoading = false;
  String? _errorMessage;

  List<ChecklistModel> get checklist => _checklist;
  List<bool> get isCheckedList => _isCheckedList;
  bool get isLoading => _isLoading;
  String? get errorMessage => _errorMessage;

  Future<void> loadInitialData() async {
    _checklist = [];
    if (_checklist.isEmpty) {
      _isLoading = true;
      _errorMessage = null;
      notifyListeners();

      final result = await _lotoService.checkList();
      if (!result['success']) {
        _isLoading = false;
        _errorMessage = result['message'];
      } else {
        _isLoading = false;
        _errorMessage = null;
        _checklist = result['userCheckList'];
        _isCheckedList = List<bool>.filled(_checklist.length, false);
      }
      notifyListeners();
    }
  }
}