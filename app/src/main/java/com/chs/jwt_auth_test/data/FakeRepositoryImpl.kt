package com.chs.jwt_auth_test.data

import android.util.Log
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
                     email = userEmail,
                     password = userPassword
                 )
            ).body()!!

            pref.putData(Constants.PREF_KEY_ACCESS_TOKEN, response.accessToken)
            pref.putData(Constants.PREF_KEY_REFRESH_TOKEN, response.refreshToken)

            ApiResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                Log.e("API_REQUEST_LOGIN", e.message())
                ApiResult.UnAuthorized()
            } else {
                Log.e("API_REQUEST_LOGIN", e.message())
                ApiResult.UnknownError()
            }
        } catch (e: Exception) {
            Log.e("API_REQUEST_LOGIN", e.message.toString())
            ApiResult.UnknownError()
        }
    }

    override suspend fun requestUserInfo(isExpiredToken: Boolean): ApiResult<UserInfo> {
        return try {
            val accessToken = pref.getData(Constants.PREF_KEY_ACCESS_TOKEN, "").run {
                if (isExpiredToken) this.plus("A")
                else this
            }

            val response: ResponseUserInfo = api.requestUserProfile(
                token = "${Constants.TOKEN_TYPE} $accessToken"
            ).body()!!

            ApiResult.Authorized(response.toUserInfo())
        } catch (e: HttpException) {
            if (e.code() == 401) {
                Log.e("API_REQUEST_USER_INFO", e.message())
                ApiResult.UnAuthorized()
            } else {
                Log.e("API_REQUEST_USER_INFO", e.message())
                ApiResult.UnknownError()
            }
        } catch (e: Exception) {
            Log.e("API_REQUEST_USER_INFO", e.message.toString())
            ApiResult.UnknownError()
        }
    }
}