import 'package:app/models/main/news_model.dart';
import 'package:dio/dio.dart' as Dio;
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class NewsService {
  final _dio = Dio.Dio();
  final _storage = const FlutterSecureStorage();
  final baseUrl = dotenv.env['API_URL2'] ?? '';

  NewsService() {
    _dio.interceptors.add(Dio.InterceptorsWrapper(
      onRequest: (options, handler) async {
        String? token = await _storage.read(key: 'token');
        String? companyCode = await _storage.read(key: 'Company-Code');
        options.headers['Device-Type'] = 'app';
        if (token != null) {
          options.headers['Authorization'] = 'Bearer $token';
          options.headers['Company-Code'] = companyCode;
        }
        return handler.next(options);
      },
    ));
  }

  ///////////////////////// newslist //////////////////////////////////
  Future<Map<String, dynamic>> getNews() async {
    try {
      Dio.Response response = await _dio.get(
        '$baseUrl/news',
      );
      if (response.statusCode == 200) {
        List<NewsModel> newsList = [];
        for (var item in response.data) {
          newsList.add(
            NewsModel.fromJson(item),
          );
        }
        return {'success': true, 'newsList': newsList};
      } else {
        return {'success': false};
      }
    } on Dio.DioException catch (e) {
      if (e.response?.data['error_code']?.startsWith('JT')) {
        await _storage.deleteAll();
        return {
          'success': false,
          'statusCode': e.response?.statusCode,
          'message': 'JT'
        };
      } else {
        return {
          'success': false,
          'statusCode': e.response?.statusCode,
          'message': e.response?.data['message'],
        };
      }
    }
  }
}
