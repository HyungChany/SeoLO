package com.seolo.seolo.services

import com.seolo.seolo.model.NewPINRequest
import com.seolo.seolo.model.PINRequest
import com.seolo.seolo.model.PINResponse
import com.seolo.seolo.model.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

// 로그인 요청
interface LoginService {
    @POST("login")
    fun login(
        @Header("Device-Type") deviceType: String, @Body loginData: Map<String, String>
    ): Call<TokenResponse>
}

// pin 로그인 요청
interface PINService {
    @POST("users/pin")
    fun sendPinNumber(
        @Header("Authorization") authorization: String,
        @Header("Company-Code") companyCode: String,
        @Header("Device-Type") deviceType: String,
        @Body pinRequest: PINRequest
    ): Call<PINResponse>
}


// pin 재설정 요청
interface NewPINService {
    @PATCH("users/pin")
    fun sendPinNumber(
        @Header("Authorization") authorization: String,
        @Header("Company-Code") companyCode: String,
        @Header("Device-Type") deviceType: String,
        @Body newPinRequest: NewPINRequest
    ): Call<String>

}
