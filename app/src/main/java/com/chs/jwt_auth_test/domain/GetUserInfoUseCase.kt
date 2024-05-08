package com.chs.jwt_auth_test.domain

import com.chs.jwt_auth_test.common.ApiResult
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: FakeRepository
) {
    suspend operator fun invoke(isExpiredToken: Boolean): ApiResult<UserInfo> {
        return repository.requestUserInfo(isExpiredToken)
    }
}