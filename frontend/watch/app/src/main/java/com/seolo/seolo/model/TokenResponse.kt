package com.seolo.seolo.model

data class TokenResponse(
    val userId: String,
    val username: String,
    val companyCode: String,
    val codeStatus: String,
    val issuedToken: IssuedToken,
    val workingLockerUid: String?,
    val workingMachineId: String?,
    val issuedCoreToken: String?
)

data class IssuedToken(
    val accessToken: String,
    val refreshToken: String
)
