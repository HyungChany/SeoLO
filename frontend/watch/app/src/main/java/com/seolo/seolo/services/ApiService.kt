package com.seolo.seolo.services

import com.seolo.seolo.model.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body loginData: Map<String, String>): Call<TokenResponse>

}
