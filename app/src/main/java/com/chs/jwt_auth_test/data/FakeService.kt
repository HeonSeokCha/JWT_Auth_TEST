package com.chs.jwt_auth_test.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface FakeService {

    @POST("auth")
    suspend fun requestLogin(
        @Body request: RequestLoginInfo
    ): Response<ResponseTokenInfo>

    @GET("/user")
    suspend fun requestUserProfile(
        @Header("Authorization") token: String
    ): Response<ResponseUserInfo>


}