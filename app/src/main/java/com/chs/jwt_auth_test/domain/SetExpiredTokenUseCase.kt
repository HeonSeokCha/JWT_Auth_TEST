package com.chs.jwt_auth_test.domain

import javax.inject.Inject

class SetExpiredTokenUseCase @Inject constructor(
    private val repository: FakeRepository
) {
    suspend operator fun invoke() {
        repository.changeTokenInfo()
    }
}