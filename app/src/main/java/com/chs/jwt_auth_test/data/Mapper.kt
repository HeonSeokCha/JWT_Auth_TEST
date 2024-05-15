package com.chs.jwt_auth_test.data

import com.chs.jwt_auth_test.data.response.ResponseUserInfo
import com.chs.jwt_auth_test.domain.UserInfo

fun ResponseUserInfo.toUserInfo(): UserInfo {
    return UserInfo(
        name = this.userName,
    )
}