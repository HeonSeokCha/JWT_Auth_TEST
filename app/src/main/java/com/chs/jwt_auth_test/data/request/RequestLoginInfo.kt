package com.chs.jwt_auth_test.data.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestLoginInfo(
    val userId: String,
    val password: String
)
