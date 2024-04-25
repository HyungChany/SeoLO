import 'package:app/models/main/news_model.dart';
import 'package:dio/dio.dart';

class NewsService {
  final _dio = Dio();

  Future<Map<String, dynamic>> getNews() async {
    try {
      final response = await _dio.get('http://192.168.100.139:8000/news');

      if (response.statusCode == 200) {
        NewsModel news = NewsModel.fromJson(response.data);
        return {'success': true, 'news': news,};
      } else {
        return {'success': false, 'statusCode': response.statusCode};
      }
    } on DioError catch (e) {
      return {'success': false, 'statusCode': e.response?.statusCode};
    }
  }
}
