package com.chs.jwt_auth_test.data.service

import com.chs.jwt_auth_test.data.request.RequestLoginInfo
import com.chs.jwt_auth_test.data.response.ResponseTokenInfo
import com.chs.jwt_auth_test.data.response.ResponseUserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface LocalService {

    @POST("auth")
    suspend fun requestLogin(
        @Body request: RequestLoginInfo
    ): Response<ResponseTokenInfo>

    @GET("/user")
    suspend fun requestUserProfile(
        @Header("Authorization") token: String
    ): Response<ResponseUserInfo>


}