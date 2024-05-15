package com.chs.jwt_auth_test.data

import android.util.Log
import com.chs.jwt_auth_test.common.Constants
import com.chs.jwt_auth_test.data.request.RequestTokenInfo
import com.chs.jwt_auth_test.data.service.TokenService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class FakeAuthenticator @Inject constructor(
    private val pref: DataStoreSource,
    private val api: TokenService
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? =
        runBlocking(Dispatchers.IO) {
            Log.e("CHS_LOG", "request")
            val currentToken: String = pref.getData(Constants.PREF_KEY_ACCESS_TOKEN, "")


            // Blocking parallel coroutines.
            val request = mutex.withLock {
                val updateToken: String = pref.getData(Constants.PREF_KEY_ACCESS_TOKEN, "")

                val token = if (currentToken != updateToken) {
                    Log.e("CHS_LOG", "not need request refresh")
                    updateToken
                } else {
                    if (response.request().header("Authorization") != updateToken) {
                        updateToken
                    } else {
                        Log.e("CHS_LOG", "need request refresh")

                        api.getRefreshTokenInfo(
                            token = RequestTokenInfo(
                                pref.getData(Constants.PREF_KEY_REFRESH_TOKEN, "")
                            )
                        ).run {
                            if (this.isSuccessful && this.body() != null) {
                                val tokenInfo = this.body()!!
                                Log.e("CHS_LOG", tokenInfo.toString())

                                pref.putData(Constants.PREF_KEY_ACCESS_TOKEN, "${Constants.TOKEN_TYPE} ${tokenInfo.accessToken}")
                                pref.putData(Constants.PREF_KEY_REFRESH_TOKEN, tokenInfo.refreshToken)

                                "${Constants.TOKEN_TYPE} ${tokenInfo.accessToken}"
                            } else null
                        }
                    }
                }

                return@withLock if (token != null) response.request().newBuilder()
                    .header("Authorization", token)
                    .build()
                else null
            }

            return@runBlocking request
        }
}