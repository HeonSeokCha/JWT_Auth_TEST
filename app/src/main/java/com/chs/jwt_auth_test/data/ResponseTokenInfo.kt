package com.chs.jwt_auth_test.data

import kotlinx.serialization.Serializable

@Serializable
data class ResponseTokenInfo(
    val accessToken: String,
    val refreshToken: String
)
