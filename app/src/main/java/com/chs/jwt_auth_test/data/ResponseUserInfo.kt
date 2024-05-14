package com.chs.jwt_auth_test.data

import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserInfo(
    val userId: String,
    val userName: String,
)
