package com.chs.jwt_auth_test.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import javax.inject.Inject


class AuthorizationIntercept @Inject constructor(
    private val pref: DataStoreSource,
    private val client: HttpClient
) {

    suspend fun authorizationIntercept() {
        client.plugin(HttpSend).intercept { req ->
            execute(req)
        }
    }
}