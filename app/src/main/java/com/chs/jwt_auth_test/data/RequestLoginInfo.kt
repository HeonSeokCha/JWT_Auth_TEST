package com.chs.jwt_auth_test.data

import kotlinx.serialization.Serializable

@Serializable
data class RequestLoginInfo(
    val email: String,
    val password: String
)
