import 'package:app/models/core/check_model.dart';
import 'package:app/services/core_service.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class CoreCheckViewModel extends ChangeNotifier {
  final _storage = const FlutterSecureStorage();
  final CoreService _coreService = CoreService();
  CoreCheckModel? _coreCheckModel;
  String? lockerUid;
  String? battery;
  String? machineId;

  bool _isLoading = false;
  String? _errorMessage;

  String? get taskType => _coreCheckModel!.taskType;

  String? get startTime => _coreCheckModel!.startTime;

  String? get endTime => _coreCheckModel!.endTime;

  String? get precaution => _coreCheckModel!.precaution;

  String? get workerName => _coreCheckModel!.workerName;

  String? get workerTeam => _coreCheckModel!.workerTeam;

  String? get workerTitle => _coreCheckModel!.workerTitle;

  String? get facilityName => _coreCheckModel!.facilityName;

  String? get machineName => _coreCheckModel!.machineName;

  String? get machineCode => _coreCheckModel!.machineCode;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  CoreCheckViewModel() {
    initializeData();
  }

  Future<void> initializeData() async {
    lockerUid = await _storage.read(key: 'locker_uid');
    battery = await _storage.read(key: 'locker_battery');
    int? batteryInfo = (battery != null) ? int.parse(battery!) : 0;
    machineId = await _storage.read(key: 'machine_id');
    int? machineIdInfo = (machineId != null) ? int.parse(machineId!) : 0;
    _coreCheckModel = CoreCheckModel(lockerUid: lockerUid ?? '',
        battery: batteryInfo,
        machineId: machineIdInfo,
        taskType: '',
        startTime: '',
        endTime: '',
        precaution: '',
        workerName: '',
        workerTeam: '',
        workerTitle: '',
        facilityName: '',
        machineName: '',
        machineCode: '');
    notifyListeners();
  }

  Future<void> coreCheck() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    await initializeData();
    
    final result = await _coreService.coreCheck(_coreCheckModel!);
    _isLoading = false;

    if (!result['success']) {
      _errorMessage = result['message'];
    } else {
      _errorMessage = null;
      _coreCheckModel = result['coreCheckModel'];
    }
    notifyListeners();
  }
}
