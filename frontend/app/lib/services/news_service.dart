import 'package:app/models/main/news_model.dart';
import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';

class NewsService {
  final _dio = Dio();

  Future<List<NewsModel>> getNews() async {
    try {
      final response = await _dio.get('http://192.168.100.139:8000/news');

      if (response.statusCode == 200) {
        List<dynamic> responseData = response.data;
        List<NewsModel> newsList = responseData.map((data) => NewsModel.fromJson(data)).toList();
        return newsList;
      } else {
        throw Exception('Failed to load news');
      }
    } on DioError catch (e) {
      throw Exception('Failed to load news: ${e.response?.statusCode}');
    }
  }

}