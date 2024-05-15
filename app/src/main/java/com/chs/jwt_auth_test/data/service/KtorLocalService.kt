package com.chs.jwt_auth_test.data.service

import com.chs.jwt_auth_test.data.request.RequestLoginInfo
import com.chs.jwt_auth_test.data.request.RequestTokenInfo
import com.chs.jwt_auth_test.data.response.ResponseTokenInfo
import com.chs.jwt_auth_test.data.response.ResponseUserInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import javax.inject.Inject

class KtorLocalService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun requestLogin(requestLoginInfo: RequestLoginInfo): ResponseTokenInfo {
        return client.post {
            url("api/auth")
            setBody(requestLoginInfo)
        }.body()
    }

    suspend fun requestUserInfo(accessToken: String): List<ResponseUserInfo> {
        return client.get {
            url("api/user")
            headers {
                append(HttpHeaders.Authorization, accessToken)
            }
        }.body()
    }

    suspend fun requestRefreshAccessToken(requestTokenInfo: RequestTokenInfo): ResponseTokenInfo {
        return client.post {
            url("api/auth/refresh")
            setBody(requestTokenInfo)
        }.body()

    }
}