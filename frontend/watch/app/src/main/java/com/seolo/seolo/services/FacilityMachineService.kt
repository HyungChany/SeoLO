package com.seolo.seolo.services

import com.seolo.seolo.model.FacilityResponse
import com.seolo.seolo.model.MachineResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FacilityService {
    @GET("facilities")
    fun getFacilities(
        @Header("Authorization") authorization: String, @Header("Company-Code") companyCode: String
    ): Call<FacilityResponse>
}

interface MachineService {
    @GET("machines/facility/{facilityId}")
    fun getMachines(
        @Path("facilityId") facilityId: String,
        @Header("Authorization") authorization: String,
        @Header("Company-Code") companyCode: String
    ): Call<MachineResponse>
}
