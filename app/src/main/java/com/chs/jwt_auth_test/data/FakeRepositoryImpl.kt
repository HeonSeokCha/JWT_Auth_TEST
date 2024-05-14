package com.chs.jwt_auth_test.data

import android.util.Log
import androidx.datastore.dataStore
import com.chs.jwt_auth_test.common.ApiResult
import com.chs.jwt_auth_test.common.Constants
import com.chs.jwt_auth_test.domain.FakeRepository
import com.chs.jwt_auth_test.domain.UserInfo
import retrofit2.HttpException
import javax.inject.Inject

class FakeRepositoryImpl @Inject constructor(
    private val api: FakeService,
    private val pref: DataStoreSource
) : FakeRepository {
    override suspend fun requestLogin(
        userEmail: String,
        userPassword: String
    ): ApiResult<Unit> {
        return try {
            val response: ResponseTokenInfo = api.requestLogin(
                 request = RequestLoginInfo(
                     userId = userEmail,
                     password = userPassword
                 )
            ).body()!!

            pref.putData(Constants.PREF_KEY_ACCESS_TOKEN, "${Constants.TOKEN_TYPE} ${response.accessToken}")
            pref.putData(Constants.PREF_KEY_REFRESH_TOKEN, response.refreshToken)
            Log.e("API_REQUEST_LOGIN", response.toString())
            ApiResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                Log.e("API_REQUEST_LOGIN", e.message())
                ApiResult.UnAuthorized()
            } else {
                Log.e("API_REQUEST_LOGIN", e.message())
                ApiResult.UnknownError(e.message.toString())
            }
        } catch (e: Exception) {
            Log.e("API_REQUEST_LOGIN", e.message.toString())
            ApiResult.UnknownError(e.message.toString())
        }
    }


    override suspend fun changeTokenInfo() {
        pref.putData(
            keyName = Constants.PREF_KEY_ACCESS_TOKEN,
            value = pref.getData(Constants.PREF_KEY_ACCESS_TOKEN, "").plus("ABCDEFG")
        )
    }

    override suspend fun requestUserInfo(): ApiResult<UserInfo> {
        return try {
            val accessToken = pref.getData(Constants.PREF_KEY_ACCESS_TOKEN, "")

            val response: ResponseUserInfo = api.requestUserProfile(
                token = accessToken
            ).body()!!

            Log.e("API_REQUEST_USER_INFO", response.toString())
            ApiResult.Authorized(response.toUserInfo())
        } catch (e: HttpException) {
            if (e.code() == 401) {
                Log.e("API_REQUEST_USER_INFO", e.message())
                ApiResult.UnAuthorized()
            } else {
                Log.e("API_REQUEST_USER_INFO", e.message())
                ApiResult.UnknownError(e.message.toString())
            }
        } catch (e: Exception) {
            Log.e("API_REQUEST_USER_INFO", e.message.toString())
            ApiResult.UnknownError(e.message.toString())
        }
    }
}