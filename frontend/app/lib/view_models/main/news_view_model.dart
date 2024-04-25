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
    if (!result['success']) {
      int statusCode = result['statusCode'];
      switch (statusCode) {
        case 500:
          _errorMessage = '서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.';
          break;
        default:
          _errorMessage = '알 수 없는 오류가 발생했습니다. 관리자에게 문의해주세요.';
      }
    } else {
      _news = result['news'];
      _isLoading = false;
    }
    notifyListeners(); // 데이터가 변경되었음을 알림
  }
}
