package com.chs.jwt_auth_test.data

import kotlinx.serialization.Serializable

@Serializable
data class RequestTokenInfo(
    val refreshToken: String
)
