package com.seolo.seolo.services

import com.seolo.seolo.model.TokenResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("path_to_token_endpoint")
    fun getToken(): Call<TokenResponse>
}
