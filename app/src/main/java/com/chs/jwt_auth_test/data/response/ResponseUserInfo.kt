package com.chs.jwt_auth_test.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserInfo(
    val userId: String,
    val userName: String,
)
