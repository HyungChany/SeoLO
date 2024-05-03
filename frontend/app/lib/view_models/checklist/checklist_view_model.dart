import 'package:flutter/material.dart';
import 'package:app/services/checklist_service.dart';
import 'package:app/models/checklist/checklist_model.dart';

class ChecklistViewModel extends ChangeNotifier{
  final ChecklistService _checklistService = ChecklistService();

  List <ChecklistModel> _Checklist = [];
  bool _isLoading = false;
  String? _errorMessage;

  List<ChecklistModel> get checklist => _Checklist;
  bool get isLoading => _isLoading;
  String? get errorMessage => _errorMessage;

  ChecklistViewModel(){
    loadInitialData();
  }

  void loadInitialData() async{
    _Checklist = [];
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _checklistService.checkList();
    if (!result['success']){
      _errorMessage = 'API 연결에 실패했습니다';
    }else{
      _errorMessage = null;
      _Checklist = result['userCheckList'];
    }
    notifyListeners();
  }
}