import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:dio/dio.dart' as Dio;
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter/material.dart';
import 'package:app/models/main/checklist_model.dart';
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

  ///////////////////////// checklist //////////////////////////////////
  Future<Map<String, dynamic>> checkList(ChecklistModel checkListModel) async {
    try {
      Dio.Response response = await _dio.get(
        '$baseUrl/checklist',
      );
      if (response.statusCode == 200) {
        List<ChecklistModel> userCheckList = [];
          for (var item in response.data['basic_checklists']) {
            userCheckList.add(
              ChecklistModel.fromJson(item),
            );
      }if (response.data['checklists'] != []){
          for (var item in response.data['checklists']){
            userCheckList.add(
              ChecklistModel.fromJson(item)
            );
          }
        }
          return {'success' : true, 'userCheckList' : userCheckList};
     }else{
        return {'success': false};
      }
    } on Dio.DioException catch (e) {
      debugPrint(e.message);
      return {'success': false, 'statusCode': e.response?.statusCode};
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