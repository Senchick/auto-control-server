package com.company.autocontrol.dto

import com.company.autocontrol.enums.Role

data class UserDto(
    val login: String,
    val password: String,
    val firstname: String,
    val surname: String,
    val role: Role = Role.USER
)
