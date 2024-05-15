package com.chs.jwt_auth_test.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseTokenInfo(
    val accessToken: String,
    val refreshToken: String
)
