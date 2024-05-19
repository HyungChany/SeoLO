import 'package:app/models/loto/machine_model.dart';
import 'package:flutter/material.dart';
import 'package:app/services/loto_service.dart';

class MachineViewModel extends ChangeNotifier {
  final LotoService _lotoService = LotoService();
  late int facilityId;

  List<MachineModel> _machines = [];
  bool _isLoading = false;
  String? _errorMessage;

  List<MachineModel> get machines => _machines;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  void setFacilityId(value) async {
    facilityId = value;
  }

  Future<void> loadInitialData() async {
    _machines = [];
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _lotoService.getMachines(facilityId);
    if (!result['success']) {
      _isLoading = false;
      _errorMessage = result['message'];
    } else {
      _isLoading = false;
      _errorMessage = null;
      _machines = result['machines'];
    }
    notifyListeners();
  }
}
