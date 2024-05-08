package com.chs.jwt_auth_test.data

import com.chs.jwt_auth_test.domain.UserInfo

fun ResponseUserInfo.toUserInfo(): UserInfo {
    return UserInfo(
        name = this.name,
        email = this.email,
        password = this.password,
        role = this.role,
        avatar = this.avatar
    )
}