package com.chs.jwt_auth_test.domain

import com.chs.jwt_auth_test.common.ApiResult

interface FakeRepository {

    suspend fun requestLogin(
        userEmail: String,
        userPassword: String
    ): ApiResult<Unit>

    suspend fun requestUserInfo(isExpiredToken: Boolean): ApiResult<UserInfo>

}