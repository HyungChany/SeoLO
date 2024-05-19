import 'package:app/models/core/unlock_model.dart';
import 'package:app/services/core_service.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class CoreUnlockViewModel extends ChangeNotifier {
  final _storage = const FlutterSecureStorage();
  final CoreService _coreService = CoreService();
  late CoreUnlockModel _coreUnlockModel;
  String? lockerUid;
  String? battery;
  String? machineId;
  String? tokenValue;
  bool _isLoading = false;
  String? _errorMessage;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  Future<void> initializeData() async {
    lockerUid = await _storage.read(key: 'locker_uid');
    battery = await _storage.read(key: 'locker_battery');
    int? batteryInfo = (battery != null) ? int.parse(battery!) : 0;
    machineId = await _storage.read(key: 'machine_id');
    int? machineIdInfo = (machineId != null) ? int.parse(machineId!) : 0;
    tokenValue = await _storage.read(key: 'locker_token');
    _coreUnlockModel = CoreUnlockModel(
        lockerUid: lockerUid ?? '',
        battery: batteryInfo,
        machineId: machineIdInfo,
        tokenValue: tokenValue ?? '');
    notifyListeners();
  }

  Future<void> coreUnlock() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    await initializeData();

    final result = await _coreService.coreUnlock(_coreUnlockModel);
    _isLoading = false;

    if (!result['success']) {
      _errorMessage = result['message'];
    } else {
      _errorMessage = null;
    }
    notifyListeners();
  }
}
