package com.seolo.seolo.model

data class PINRequest(
    val pin: String
)

data class NewPINRequest(
    val new_pin: String
)

data class PINResponse(
    val fail_count: Int?,
    val auth_error_code: String?,
    val authenticated: Boolean
)
