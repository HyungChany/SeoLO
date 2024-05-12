package com.seolo.seolo.services

import com.seolo.seolo.model.ChecklistResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ChecklistService {
    @GET("checklists")
    fun getChecklists(
        @Header("Authorization") authorization: String,
        @Header("Company-Code") companyCode: String
    ): Call<ChecklistResponse>
}