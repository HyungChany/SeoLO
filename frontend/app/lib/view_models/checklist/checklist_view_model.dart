import 'package:flutter/material.dart';
import 'package:app/services/loto_service.dart';
import 'package:app/models/checklist/checklist_model.dart';

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

  ChecklistViewModel(){
    loadInitialData();
  }

  void loadInitialData() async{
    _checklist = [];
    _isCheckedList = [];
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _lotoService.checkList();
    if (!result['success']){
      _errorMessage = 'API 연결에 실패했습니다';
    }else{
      _errorMessage = null;
      _checklist = result['userCheckList'];
      for (int i = 0; i < result['userCheckList'].length; i++) {
        _isCheckedList.add(false);
      }
    }
    notifyListeners();
  }
}