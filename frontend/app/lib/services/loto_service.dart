import 'package:app/models/loto/facility_model.dart';
import 'package:app/models/loto/machine_model.dart';
import 'package:app/models/loto/task_templates_model.dart';
import 'package:app/models/user/my_info_model.dart';
import 'package:app/view_models/user/my_info_view_model.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:dio/dio.dart' as Dio;
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:app/models/loto/checklist_model.dart';

class LotoService {
  final _dio = Dio.Dio();
  final _storage = const FlutterSecureStorage();
  final baseUrl = dotenv.env['API_URL'] ?? '';

  LotoService() {
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

  ///////////////////////// checklist //////////////////////////////////
  Future<Map<String, dynamic>> checkList() async {
    try {
      Dio.Response response = await _dio.get(
        '$baseUrl/checklists',
      );
      if (response.statusCode == 200) {
        List<ChecklistModel> userCheckList = [];
        for (var item in response.data['basic_checklists']) {
          userCheckList.add(
            ChecklistModel.fromJson(item),
          );
        }
        if (response.data['checklists'] != []) {
          for (var item in response.data['checklists']) {
            userCheckList.add(ChecklistModel.fromJson(item));
          }
        }
        return {'success': true, 'userCheckList': userCheckList};
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

  ///////////////////////// facility //////////////////////////////////
  Future<Map<String, dynamic>> getFacilities() async {
    final MyInfoViewModel myInfoViewModel = MyInfoViewModel();
    MyInfoModel myInfoModel;

    await myInfoViewModel.myInfo();
    myInfoModel = myInfoViewModel.myInfoModel!;

    try {
      Dio.Response response = await _dio.get(
        '$baseUrl/facilities/${myInfoModel.employeeNum}',
      );
      if (response.statusCode == 200) {
        List<FacilityModel> facilities = [];
        for (var item in response.data['facilities']) {
          facilities.add(
            FacilityModel.fromJson(item),
          );
        }

        return {'success': true, 'facilities': facilities};
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
  ///////////////////////// machine //////////////////////////////////
  Future<Map<String, dynamic>> getMachines(int facilityID) async {
    try {
      Dio.Response response = await _dio.get(
        '$baseUrl/machines/facilities/$facilityID/easy',
      );
      if (response.statusCode == 200) {
        List<MachineModel> machines = [];
        for (var item in response.data['machine_id_name_list']) {
          machines.add(
            MachineModel.fromJson(item),
          );
        }

        return {'success': true, 'machines': machines};
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

  ///////////////////////// task template //////////////////////////////////
  Future<Map<String, dynamic>> getTaskTemplates() async {
    try {
      Dio.Response response = await _dio.get(
        '$baseUrl/task-templates',
      );
      if (response.statusCode == 200) {
        List<TaskTemplatesModel> templates = [];
        for (var template in response.data['templates']) {
          templates.add(
            TaskTemplatesModel.fromJson(template),
          );
        }
        return {'success': true, 'templates': templates};
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
