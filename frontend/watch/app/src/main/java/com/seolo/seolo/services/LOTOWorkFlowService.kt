package com.seolo.seolo.services

import com.seolo.seolo.model.FacilityResponse
import com.seolo.seolo.model.MachineResponse
import com.seolo.seolo.model.TaskResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

// 사원에게 할당된 작업장 조회
interface FacilityService {
    @GET("facilities/{username}")
    fun getFacilities(
        @Header("Authorization") authorization: String,
        @Header("Company-Code") companyCode: String,
        @Path("username") username: String
    ): Call<FacilityResponse>

}

// 작업장별 장비 목록 간단조회
interface MachineService {
    @GET("machines/facilities/{facilityId}/easy")
    fun getMachines(
        @Header("Authorization") authorization: String,
        @Header("Company-Code") companyCode: String,
        @Path("facilityId") facilityId: String
    ): Call<MachineResponse>
}


// 작업 내용 템플릿 조회
interface TaskService {
    @GET("task-templates")
    fun getTasks(
        @Header("Authorization") authorization: String,
        @Header("Company-Code") companyCode: String
    ): Call<TaskResponse>
}