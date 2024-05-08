package com.chs.jwt_auth_test.domain

data class UserInfo(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val avatar: String
)
