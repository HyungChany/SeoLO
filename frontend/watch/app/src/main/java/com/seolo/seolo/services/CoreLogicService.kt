package com.seolo.seolo.services

import com.seolo.seolo.model.IssueResponse
import com.seolo.seolo.model.LotoInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface IssueService {
    @POST("core/ISSUE")
    fun sendLotoInfo(
        @Header("Authorization") authorization: String,
        @Header("Company-Code") companyCode: String,
        @Header("Device-Type") deviceType: String,
        @Body lotoInfo: LotoInfo
    ): Call<IssueResponse>
}