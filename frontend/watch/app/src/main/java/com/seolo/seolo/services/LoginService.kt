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

interface LoginService {
    @POST("login")
    fun login(@Body loginData: Map<String, String>): Call<TokenResponse>

}

interface PINService {
    @POST("users/pin")
    fun sendPinNumber(
        @Header("Authorization") authorization: String,
        @Header("Company-Code") companyCode: String,
        @Body pinRequest: PINRequest
    ): Call<PINResponse>
}

interface NewPINService {
    @PATCH("users/pin")
    fun sendPinNumber(
        @Header("Authorization") authorization: String,
        @Header("Company-Code") companyCode: String,
        @Body newPinRequest: NewPINRequest
    ): Call<String>

}
