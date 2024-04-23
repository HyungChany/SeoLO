import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

class TestService {
  final _dio = Dio();
  final baseUrl = dotenv.get('API_URL') ?? '';

  void getTest() async {
    final response = await _dio.get(
      '$baseUrl/',
    );
    debugPrint(response.toString());
  }
}
