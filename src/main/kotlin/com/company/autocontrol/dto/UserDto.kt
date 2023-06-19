package com.company.autocontrol.dto

import com.company.autocontrol.enums.Role
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserDto(
    @field:NotEmpty(message = "{login.notEmpty}")
    @field:Size(min = 4, max = 32, message = "{login.size}")
    val login: String = "",

    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
        message = "{password.pattern}"
    )
    @field:NotEmpty(message = "{password.notEmpty}")
    @field:Size(min = 4, max = 32, message = "{password.size}")
    val password: String = "",

    @field:NotEmpty(message = "{firstname.notEmpty}")
    @field:Size(min = 2, max = 16, message = "{firstname.size}")
    val firstname: String = "",

    @field:NotEmpty(message = "{surname.notEmpty}")
    @field:Size(min = 2, max = 16, message = "{surname.size}")
    val surname: String = "",

    @field:NotEmpty(message = "{department.notEmpty}")
    @field:Size(min = 5, max = 64, message = "{department.size}")
    val department: String = "",

    val role: Role = Role.USER
)
