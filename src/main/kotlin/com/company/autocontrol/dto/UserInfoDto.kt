package com.company.autocontrol.dto

import com.company.autocontrol.enums.Role

data class UserInfoDto(
    val login: String = "",
    val firstname: String = "",
    val surname: String = "",
    val department: String = "",
    val role: Role = Role.USER
)
