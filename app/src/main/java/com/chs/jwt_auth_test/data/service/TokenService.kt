package com.chs.jwt_auth_test.data.service

import com.chs.jwt_auth_test.data.request.RequestTokenInfo
import com.chs.jwt_auth_test.data.response.ResponseTokenInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {

    @POST("auth/refresh")
    suspend fun getRefreshTokenInfo(
        @Body token: RequestTokenInfo
    ): Response<ResponseTokenInfo>
}