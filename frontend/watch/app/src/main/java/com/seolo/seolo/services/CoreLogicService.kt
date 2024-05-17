package com.seolo.seolo.services

import com.seolo.seolo.model.IssueResponse
import com.seolo.seolo.model.LotoInfo
import com.seolo.seolo.model.LotoLOCKInfo
import com.seolo.seolo.model.LotoLockInfo
import com.seolo.seolo.model.LotoUnlockInfo
import com.seolo.seolo.model.UnlockResponse
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

interface LockedService {
    @POST("core/LOCKED")
    fun sendLockedInfo(
        @Header("Authorization") authorization: String,
        @Header("Company-Code") companyCode: String,
        @Header("Device-Type") deviceType: String,
        @Body lotoLockInfo: LotoLockInfo
    ): Call<LotoLOCKInfo>
}

interface UnlockService {
    @POST("core/UNLOCK")
    fun sendUnlockInfo(
        @Header("Authorization") authorization: String,
        @Header("Company-Code") companyCode: String,
        @Header("Device-Type") deviceType: String,
        @Body lotoUnlockInfo: LotoUnlockInfo
    ): Call<UnlockResponse>
}