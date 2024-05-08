package com.chs.jwt_auth_test.presentation

import com.chs.jwt_auth_test.common.ApiResult
import com.chs.jwt_auth_test.domain.UserInfo

data class FakeState(
    val isLoading: Boolean = false,
    val userInfo: ApiResult<UserInfo>? = null,
    val anotherUsersInfo: List<ApiResult<UserInfo>> = emptyList()
)
