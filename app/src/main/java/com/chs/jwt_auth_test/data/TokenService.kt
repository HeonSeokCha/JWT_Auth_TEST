package com.chs.jwt_auth_test.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenService {

    @POST("auth/refresh-token")
    suspend fun getRefreshTokenInfo(
        @Body token: RequestTokenInfo
    ): Response<ResponseTokenInfo>
}