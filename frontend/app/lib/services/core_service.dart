import 'package:app/models/core/check_model.dart';
import 'package:app/models/core/issue_model.dart';
import 'package:app/models/core/locked_model.dart';
import 'package:app/models/core/unlock_model.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:dio/dio.dart' as Dio;
import 'package:flutter_dotenv/flutter_dotenv.dart';

class CoreService {
  final _dio = Dio.Dio();
  final _storage = const FlutterSecureStorage();
  final baseUrl = dotenv.env['API_URL'] ?? '';

  CoreService() {
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

  ///////////////////////// issue //////////////////////////////////
  Future<Map<String, dynamic>> coreIssue(CoreIssueModel coreIssueModel) async {
    try {
      Dio.Response response = await _dio.post(
        '$baseUrl/core/ISSUE',
        data: coreIssueModel.toJson(),
      );
      if (response.statusCode == 200) {
        await _storage.write(
            key: 'Core-Code', value: response.data['next_code']);
        await _storage.write(
            key: 'locker_token', value: response.data['token_value']);
        await _storage.write(
            key: 'machine_id', value: coreIssueModel.machineId.toString());
        return {
          'success': true,
        };
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

  ///////////////////////// check //////////////////////////////////
  Future<Map<String, dynamic>> coreCheck(CoreCheckModel coreCheckModel) async {
    try {
      Dio.Response response =
          await _dio.post('$baseUrl/core/CHECK', data: coreCheckModel.toJson());
      if (response.statusCode == 200) {
        await _storage.delete(key: 'locker_uid');
        await _storage.delete(key: 'machine_id');
        await _storage.delete(key: 'locker_battery');
        CoreCheckModel coreCheckModel =
            CoreCheckModel.fromJson(response.data['check_more_response']);
        coreCheckModel.taskType = response.data['task_history']['taskTemplate']
                ['task_type']
            .toString();
        coreCheckModel.startTime =
            response.data['task_history']['taskStartDateTime'].toString();
        coreCheckModel.endTime = response.data['task_history']
                ['taskEndEstimatedDateTime']
            .toString();
        coreCheckModel.precaution =
            response.data['task_history']['taskPrecaution'].toString();
        return {'success': true, 'coreCheckModel': coreCheckModel};
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

  ///////////////////////// unlock //////////////////////////////////
  Future<Map<String, dynamic>> coreUnlock(
      CoreUnlockModel coreUnlockModel) async {
    try {
      Dio.Response response = await _dio.post('$baseUrl/core/UNLOCK',
          data: coreUnlockModel.toJson());
      if (response.statusCode == 200) {
        await _storage.delete(key: 'Core-Code');
        await _storage.delete(key: 'machine_id');
        await _storage.delete(key: 'locker_uid');
        await _storage.delete(key: 'locker_token');
        await _storage.delete(key: 'locker_battery');
        return {
          'success': true,
        };
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

  ///////////////////////// locked //////////////////////////////////
  Future<Map<String, dynamic>> coreLocked(
      CoreLockedModel coreLockedModel) async {
    try {
      Dio.Response response = await _dio.post('$baseUrl/core/LOCKED',
          data: coreLockedModel.toJson());
      if (response.statusCode == 200) {
        await _storage.write(
            key: 'Core-Code', value: response.data['next_code']);
        return {
          'success': true,
        };
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
