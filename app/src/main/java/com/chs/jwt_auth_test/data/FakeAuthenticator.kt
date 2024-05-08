package com.chs.jwt_auth_test.data

import com.chs.jwt_auth_test.common.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class FakeAuthenticator @Inject constructor(
    private val pref: DataStoreSource,
    private val api: FakeService
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentRefreshToken: String = runBlocking(Dispatchers.IO) {
            pref.getData(Constants.PREF_KEY_ACCESS_TOKEN, "")
        }

        //Block Multi Coroutines
        synchronized(this) {
            val updateRefreshToken: String = runBlocking(Dispatchers.IO) {
                pref.getData(Constants.PREF_KEY_ACCESS_TOKEN, "")
            }

            val token = if (currentRefreshToken != updateRefreshToken) updateRefreshToken
            else {
                runBlocking { api.getRefreshTokenInfo(token = updateRefreshToken) }.run {
                    if (this.isSuccessful && this.body() != null) {
                        val tokenInfo = this.body()!!
                        runBlocking {
                            pref.putData(Constants.PREF_KEY_ACCESS_TOKEN, tokenInfo.accessToken)
                            pref.putData(Constants.PREF_KEY_REFRESH_TOKEN, tokenInfo.refreshToken)
                        }
                        tokenInfo.accessToken
                    } else null
                }
            }
            return if (token != null) response.request().newBuilder()
                .header("Authorization", "${Constants.TOKEN_TYPE} $token")
                .build()
            else null
        }
    }
}