import 'package:app/models/loto/facility_model.dart';
import 'package:flutter/material.dart';
import 'package:app/services/loto_service.dart';

class FacilityViewModel extends ChangeNotifier {
  final LotoService _lotoService = LotoService();

  List<FacilityModel> _facilities = [];
  bool _isLoading = false;
  String? _errorMessage;

  List<FacilityModel> get facilities => _facilities;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  Future<void> loadInitialData() async {
    _facilities = [];
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _lotoService.getFacilities();
    if (!result['success']) {
      _isLoading = false;
      _errorMessage = result['message'];
    } else {
      _isLoading = false;
      _errorMessage = null;
      _facilities = result['facilities'];
    }
    notifyListeners();
  }
}
