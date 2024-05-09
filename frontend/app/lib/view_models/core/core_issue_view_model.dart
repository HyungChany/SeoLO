import 'package:app/models/core/issue_model.dart';
import 'package:app/services/core_service.dart';
import 'package:app/view_models/user/my_info_view_model.dart';
import 'package:flutter/material.dart';

class CoreIssueViewModel extends ChangeNotifier {
  final CoreService _coreService = CoreService();

  CoreIssueModel _coreIssueModel = CoreIssueModel(
    lockerUid: '',
    machineId: 0,
    taskTemplateId: 0,
    taskPrecaution: '',
    endTime: '',
    facilityName: '',
    machineName: '',
    machineCode: '',
    manager: '',
    taskTemplateName: '',);

  bool _isFocused = false;
  bool _isLoading = false;
  String? _errorMessage;

  String get facilityName => _coreIssueModel.facilityName;

  String get machineName => _coreIssueModel.machineName;

  String get machineCode => _coreIssueModel.machineCode;

  String get manager => _coreIssueModel.manager;

  String get endTime => _coreIssueModel.endTime;

  String get taskTemplateName => _coreIssueModel.taskTemplateName;

  String get taskPrecaution => _coreIssueModel.taskPrecaution;

  bool get isFocused => _isFocused;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  void setLockerUid(String value) {
    _coreIssueModel = CoreIssueModel(lockerUid: value,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        machineCode: _coreIssueModel.machineCode,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName);
    notifyListeners();
  }

  void setMachineId(int value) {
    _coreIssueModel = CoreIssueModel(lockerUid: _coreIssueModel.lockerUid,
        machineId: value,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        machineCode: _coreIssueModel.machineCode,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName);
    notifyListeners();
  }

  void setTaskTemplateId(int value) {
    _coreIssueModel = CoreIssueModel(lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: value,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        machineCode: _coreIssueModel.machineCode,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName);
    notifyListeners();
  }

  void setTaskPrecaution(String value) {
    _coreIssueModel = CoreIssueModel(lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: value,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        machineCode: _coreIssueModel.machineCode,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName);
    notifyListeners();
  }

  void setEndTime(String value) {
    _coreIssueModel = CoreIssueModel(lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: value,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        machineCode: _coreIssueModel.machineCode,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName);
    notifyListeners();
  }

  void setFacilityName(String value) {
    _coreIssueModel = CoreIssueModel(lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: value,
        machineName: _coreIssueModel.machineName,
        machineCode: _coreIssueModel.machineCode,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName);
    notifyListeners();
  }

  void setMachineName(String value) {
    _coreIssueModel = CoreIssueModel(lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: value,
        machineCode: _coreIssueModel.machineCode,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName);
    notifyListeners();
  }

  void setMachineCode(String value) {
    _coreIssueModel = CoreIssueModel(lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        machineCode: value,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName);
    notifyListeners();
  }

  void setManager(String value) {
    _coreIssueModel = CoreIssueModel(lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        machineCode: _coreIssueModel.machineCode,
        manager: value,
        taskTemplateName: _coreIssueModel.taskTemplateName);
    notifyListeners();
  }

  void setTaskTemplateName(String value) {
    _coreIssueModel = CoreIssueModel(lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        machineCode: _coreIssueModel.machineCode,
        manager: _coreIssueModel.manager,
        taskTemplateName: value);
    notifyListeners();
  }

  void setFocused(bool value) {
    _isFocused = value;
    notifyListeners();
  }

  Future<void> coreIssue() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _coreService.coreIssue(_coreIssueModel);
    _isLoading = false;

    if (!result['success']) {
      _errorMessage = result['message'];
    } else {
      _errorMessage = null;
    }
    notifyListeners();
  }
}
