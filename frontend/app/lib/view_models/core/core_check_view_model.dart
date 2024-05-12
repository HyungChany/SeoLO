import 'package:app/models/core/check_model.dart';
import 'package:app/services/core_service.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class CoreCheckViewModel extends ChangeNotifier {
  final _storage = const FlutterSecureStorage();
  final CoreService _coreService = CoreService();
  late CoreCheckModel _coreCheckModel;
  late String? lockerUid;
  late String? battery;
  late String? machineId;


  bool _isLoading = false;
  String? _errorMessage;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  Future<void> initializeData() async {
    lockerUid = await _storage.read(key: 'locker_uid');
    battery = await _storage.read(key: 'locker_battery');
    machineId = await _storage.read(key: 'machine_id');
    _coreCheckModel = CoreCheckModel(lockerUid: lockerUid, battery: battery, machineId: machineId);
    notifyListeners();
  }

  Future<void> coreCheck() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    await initializeData();

    final result = await _coreService.coreCheck(_coreCheckModel);
    _isLoading = false;

    if (!result['success']) {
      _errorMessage = result['message'];
    } else {
      _errorMessage = null;
    }
    notifyListeners();
  }
}
