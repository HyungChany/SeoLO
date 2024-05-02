import 'package:app/models/user/login_model.dart';
import 'package:app/models/user/pin_login_model.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:dio/dio.dart' as Dio;
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter/material.dart';

class UserService {
  final _dio = Dio.Dio();
  final _storage = FlutterSecureStorage();
  final baseUrl = dotenv.env['API_URL'] ?? '';

  UserService() {
    _dio.interceptors.add(Dio.InterceptorsWrapper(
      onRequest: (options, handler) async {
        String? cookie = await _storage.read(key: 'jsessionid');
        String? companyCode = await _storage.read(key: 'companyCode');
        if (cookie != null) {
          options.headers['Cookie'] = 'JSESSIONID=$cookie';
          options.headers['Company-Code'] = companyCode;
        }
        return handler.next(options);
      },
    ));
    _dio.interceptors.add(LoggingInterceptor());
  }

  ///////////////////////// id 로그인 //////////////////////////////////
  Future<Map<String, dynamic>> login(LoginModel loginModel) async {
    final formData = Dio.FormData.fromMap({
      'username': loginModel.username,
      'password': loginModel.password,
      'companyCode': loginModel.companyCode
    });

    try {
      Dio.Response response = await _dio.post('$baseUrl/login', data: formData);

      if (response.statusCode == 200) {
        String? cookie = response.data['jsessionid'];
        String? companyCode = loginModel.companyCode.toString();
        if (cookie != null) {
          await _storage.delete(key: 'jsessionid');
          await _storage.delete(key: 'companyCode');
          await _storage.write(key: 'jsessionid', value: cookie);
          await _storage.write(key: 'companyCode', value: companyCode);
          return {'success': true};
        } else {
          return {'success': false};
        }
      } else {
        return {
          'success': false,
          'statusCode': response.statusCode,
          'message': response.data['message']
        };
      }
    } on Dio.DioException catch (e) {
      debugPrint(e.message);
      return {'success': false, 'statusCode': e.response?.statusCode};
    }
  }

  ///////////////////////// 로그아웃 //////////////////////////////////
  Future<void> logout() async {
    Dio.Response response = await _dio.post(
      '$baseUrl/logout',
    );

    if (response.statusCode == 200) {
      await _storage.delete(key: 'jsessionid');
      await _storage.delete(key: 'companyCode');
    }
  }

  ///////////////////////// pin 로그인 //////////////////////////////////
  Future<Map<String, dynamic>> pinLogin(PinLoginModel pinLoginModel) async {
    try {
      Dio.Response response = await _dio.post(
        '$baseUrl/users/pin', data: pinLoginModel.toJson(),
      );
      if (response.statusCode == 200) {
        if (response.data['authenticated'] == true) {
          return {'success': true};
        } else {
          return {'success': false, 'message': 'pin 번호 틀림'};
        }
      } else {
        return {'success': false, 'message': '무언가 잘못되었다'};
      }
    } on Dio.DioException catch (e) {
      debugPrint(e.message);
      return {'success': false, 'statusCode': e.response?.statusCode, 'message': '잘못되었음'};
    }
  }
}

class LoggingInterceptor extends Dio.Interceptor {
  @override
  void onRequest(Dio.RequestOptions options,
      Dio.RequestInterceptorHandler handler) {
    debugPrint("REQUEST[${options.method}] => PATH: ${options.path}");
    debugPrint("Request Header: ${options.headers}");
    super.onRequest(options, handler);
  }

  @override
  void onResponse(Dio.Response response,
      Dio.ResponseInterceptorHandler handler) {
    debugPrint(
        "RESPONSE[${response.statusCode}] => PATH: ${response.requestOptions
            .path}");
    super.onResponse(response, handler);
  }

  @override
  void onError(Dio.DioError err, Dio.ErrorInterceptorHandler handler) {
    debugPrint(
        "ERROR[${err.response?.statusCode}] => PATH: ${err.requestOptions
            .path}");
    super.onError(err, handler);
  }
}
