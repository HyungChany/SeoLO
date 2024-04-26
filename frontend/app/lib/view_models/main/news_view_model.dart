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


  NewsViewModel() {
    loadInitialData();
  }

  void loadInitialData() async {
    _news = [];
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    final result = await _newsService.getNews();
    _news = result;
    notifyListeners(); // 데이터가 변경되었음을 알림
  }
}
