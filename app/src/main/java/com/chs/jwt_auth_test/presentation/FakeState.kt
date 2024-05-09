package com.chs.jwt_auth_test.presentation

import com.chs.jwt_auth_test.common.ApiResult
import com.chs.jwt_auth_test.domain.UserInfo


data class FakeState(
    val isLoading: Boolean = false,
    val loginState: ApiResult<Unit>? = null,
    val userState: ApiResult<UserInfo>? = null,
    val isError: String? = null
)
