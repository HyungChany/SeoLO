package com.seolo.seolo.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://k10c104.p.ssafy.io/api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    // 로그인 토큰 요청
    val loginService: LoginService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(LoginService::class.java)
    }

    // PIN 번호 확인 요청
    val pinService: PINService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PINService::class.java)
    }

    // PIN 번호 변경 요청
    val NewPinService: NewPINService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewPINService::class.java)
    }

    // 체크리스트 목록 요청
    val checklistService: ChecklistService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ChecklistService::class.java)
    }

    // 사원에게 할당된 작업장 목록 요청
    val facilityService: FacilityService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(FacilityService::class.java)
    }

    // 작업장별 장비 목록 요청
    val machineService: MachineService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MachineService::class.java)
    }

    // 작업 내용 템플릿 요청
    val taskService: TaskService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(TaskService::class.java)
    }
}