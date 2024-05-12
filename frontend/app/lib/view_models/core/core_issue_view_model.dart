import 'package:app/models/core/issue_model.dart';
import 'package:app/models/user/my_info_model.dart';
import 'package:app/services/core_service.dart';
import 'package:app/view_models/user/my_info_view_model.dart';
import 'package:flutter/material.dart';

class CoreIssueViewModel extends ChangeNotifier {
  final CoreService _coreService = CoreService();
  final MyInfoViewModel _myInfoViewModel = MyInfoViewModel();

  CoreIssueViewModel() {
    _fetchMyInfo();
  }

  late MyInfoModel myInfo;
  late CoreIssueModel _coreIssueModel;


  Future<void> _fetchMyInfo() async {
    await _myInfoViewModel.myInfo();
    myInfo = _myInfoViewModel.myInfoModel!;

    final manager = '${myInfo.employeeTeam} ${myInfo.employeeName} ${myInfo.employeeTitle}';

    _coreIssueModel = CoreIssueModel(
      lockerUid: '',
      machineId: 0,
      taskTemplateId: 0,
      taskPrecaution: '',
      endTime: '',
      facilityName: '',
      machineName: '',
      manager: manager,
      taskTemplateName: '',
      endDay: '',
    );

    notifyListeners();
  }


  bool _isLoading = false;
  String? _errorMessage;

  String get facilityName => _coreIssueModel.facilityName;

  String get machineName => _coreIssueModel.machineName;

  String get manager => _coreIssueModel.manager;

  String get endTime => _coreIssueModel.endTime;

  String get taskTemplateName => _coreIssueModel.taskTemplateName;

  String get taskPrecaution => _coreIssueModel.taskPrecaution;

  String get endDay => _coreIssueModel.endDay;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  void setLockerUid(String value) {
    _coreIssueModel = CoreIssueModel(
        lockerUid: value,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName,
    endDay: _coreIssueModel.endDay);
    notifyListeners();
  }

  void setMachineId(int value) {
    _coreIssueModel = CoreIssueModel(
        lockerUid: _coreIssueModel.lockerUid,
        machineId: value,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName,
        endDay: _coreIssueModel.endDay);
    notifyListeners();
  }

  void setTaskTemplateId(int value) {
    _coreIssueModel = CoreIssueModel(
        lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: value,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName,
        endDay: _coreIssueModel.endDay);
    notifyListeners();
  }

  void setTaskPrecaution(String value) {
    _coreIssueModel = CoreIssueModel(
        lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: value,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName,
        endDay: _coreIssueModel.endDay);
    notifyListeners();
  }

  void setEndTime(String value) {
    _coreIssueModel = CoreIssueModel(
        lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: '${_coreIssueModel.endDay}T$value:00',
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName,
        endDay: _coreIssueModel.endDay);
    notifyListeners();
  }

  void setFacilityName(String value) {
    _coreIssueModel = CoreIssueModel(
        lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: value,
        machineName: _coreIssueModel.machineName,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName,
        endDay: _coreIssueModel.endDay);
    notifyListeners();
  }

  void setMachineName(String value) {
    _coreIssueModel = CoreIssueModel(
        lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: value,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName,
        endDay: _coreIssueModel.endDay);
    notifyListeners();
  }

  void setTaskTemplateName(String value) {
    _coreIssueModel = CoreIssueModel(
        lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        manager: _coreIssueModel.manager,
        taskTemplateName: value,
        endDay: _coreIssueModel.endDay);
    notifyListeners();
  }

  void setEndDay(String value) {
    _coreIssueModel = CoreIssueModel(
        lockerUid: _coreIssueModel.lockerUid,
        machineId: _coreIssueModel.machineId,
        taskTemplateId: _coreIssueModel.taskTemplateId,
        taskPrecaution: _coreIssueModel.taskPrecaution,
        endTime: _coreIssueModel.endTime,
        facilityName: _coreIssueModel.facilityName,
        machineName: _coreIssueModel.machineName,
        manager: _coreIssueModel.manager,
        taskTemplateName: _coreIssueModel.taskTemplateName,
        endDay: value);
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
