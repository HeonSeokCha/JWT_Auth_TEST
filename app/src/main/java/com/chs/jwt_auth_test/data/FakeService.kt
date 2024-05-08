package com.chs.jwt_auth_test.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface FakeService {

    @POST("auth/login")
    suspend fun requestLogin(
        @Body request: RequestLoginInfo
    ): Response<ResponseTokenInfo>

    @GET("auth/profile")
    suspend fun requestUserProfile(
        @Header("Authorization") token: String
    ): Response<ResponseUserInfo>

    @POST("auth/refresh-token")
    suspend fun getRefreshTokenInfo(
        @Header("refreshToken") token: String
    ): Response<ResponseTokenInfo>
}