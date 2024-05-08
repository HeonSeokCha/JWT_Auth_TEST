package com.chs.jwt_auth_test.domain

import com.chs.jwt_auth_test.common.ApiResult
import javax.inject.Inject

class RequestLoginUseCase @Inject constructor(
    private val repository: FakeRepository
) {
    suspend operator fun invoke(
        userEmail: String,
        userPassword: String
    ): ApiResult<Unit> {
        return repository.requestLogin(
            userEmail = userEmail,
            userPassword = userPassword
        )
    }
}