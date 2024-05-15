package com.seolo.seolo.model

data class TokenResponse(
    val userId: String,
    val username: String,
    val companyCode: String,
    val issuedToken: IssuedToken
)

data class IssuedToken(
    val accessToken: String,
    val refreshToken: String
)
