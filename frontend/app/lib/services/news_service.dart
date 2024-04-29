import 'package:app/models/main/news_model.dart';
import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

class NewsService {
  final _dio = Dio();
  final baseUrl = dotenv.get('API_URL2') ?? '';
  Future<List<NewsModel>> getNews() async {
    try {
      final response = await _dio.get('$baseUrl/news');

      if (response.statusCODE == 200) {
        List<dynamic> responseData = response.data;
        List<NewsModel> newsList = responseData.map((data) => NewsModel.fromJson(data)).toList();
        // debugPrint('$newsList');
        return newsList;
      } else {
        throw Exception('뉴스 로드 실패');
      }
    } on DioError catch (e) {
      throw Exception('뉴스 로드 실패: ${e.response?.statusCODE}');
    }
  }

}