import 'package:app/models/main/news_model.dart';
import 'package:dio/dio.dart' as Dio;
import 'package:flutter/cupertino.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class NewsService {
  final _dio = Dio.Dio();
  final _storage = FlutterSecureStorage();
  final baseUrl = dotenv.env['API_URL2'] ?? '';

  NewsService() {
    _dio.interceptors.add(Dio.InterceptorsWrapper(
      onRequest: (options, handler) async {
        String? token = await _storage.read(key: 'token');
        String? companyCode = await _storage.read(key: 'Company-Code');
        if (token != null) {
          options.headers['Authorization'] = 'Bearer $token';
          options.headers['Company-Code'] = companyCode;
          options.headers['Device-Type'] = 'app';
        }
        return handler.next(options);
      },
    ));
    _dio.interceptors.add(LoggingInterceptor());
  }


  Future<List<NewsModel>> getNews() async {
    // try {
      final response = await _dio.get('$baseUrl/news');

      // if (response.statusCODE == 200) {
        List<dynamic> responseData = response.data;
        List<NewsModel> newsList = responseData.map((data) => NewsModel.fromJson(data)).toList();
        // debugPrint('$newsList');
        return newsList;
      // } else {
        throw Exception('뉴스 로드 실패');
      // }
    // } on DioError catch (e) {
    //   throw Exception('뉴스 로드 실패: ${e.response?.statusCODE}');
    // }
  }
}

class LoggingInterceptor extends Dio.Interceptor {
  @override
  void onRequest(
      Dio.RequestOptions options, Dio.RequestInterceptorHandler handler) {
    debugPrint("REQUEST[${options.method}] => PATH: ${options.path}");
    debugPrint("Request Header: ${options.headers}");
    super.onRequest(options, handler);
  }

  @override
  void onResponse(
      Dio.Response response, Dio.ResponseInterceptorHandler handler) {
    debugPrint(
        "RESPONSE[${response.statusCode}] => PATH: ${response.requestOptions.path}");
    super.onResponse(response, handler);
  }

  @override
  void onError(Dio.DioError err, Dio.ErrorInterceptorHandler handler) {
    debugPrint(
        "ERROR[${err.response?.statusCode}] => PATH: ${err.requestOptions.path}");
    super.onError(err, handler);
  }
}
