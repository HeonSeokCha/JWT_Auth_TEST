package com.chs.jwt_auth_test.common

sealed class ApiResult<T>(
    val data: T? = null
) {
    class Authorized<T>(data: T? = null) : ApiResult<T>(data)
    class UnAuthorized<T> : ApiResult<T>()
    class UnknownError<T> : ApiResult<T>()
}