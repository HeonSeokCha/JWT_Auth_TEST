package com.chs.jwt_auth_test.data

import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserInfo(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val role: String,
    val avatar: String
)
