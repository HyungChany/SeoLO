import 'package:app/models/main/news_model.dart';
import 'package:app/services/news_service.dart';
import 'package:flutter/material.dart';

class NewsViewModel extends ChangeNotifier {
  final NewsService _newsService = NewsService();

  List<NewsModel> _news = [];
  bool _isLoading = false;
  String? _errorMessage;

  List<NewsModel> get news => _news;

  bool get isLoading => _isLoading;

  String? get errorMessage => _errorMessage;

  Future<void> loadInitialData() async {
    _news = [];
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _newsService.getNews();
    if (!result['success']) {
      _isLoading = false;
      _errorMessage = result['message'];
    } else {
      _isLoading = false;
      _errorMessage = null;
      _news = result['newsList'];
    }
    notifyListeners();
  }
}
