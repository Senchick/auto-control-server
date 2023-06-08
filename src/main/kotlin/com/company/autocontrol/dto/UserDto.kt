package com.company.autocontrol.dto

import com.company.autocontrol.enums.Role
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserDto(
    @field:NotEmpty(message = "Login is required")
    @field:Size(min = 4, max = 32, message = "Invalid login")
    val login: String,

    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
        message = "Invalid password"
    )
    @field:NotEmpty(message = "Password is required")
    @field:Size(min = 4, max = 32, message = "Invalid password")
    val password: String,

    @field:NotEmpty(message = "Firstname is required")
    @field:Size(min = 2, max = 16, message = "Invalid firstname")
    val firstname: String,

    @field:NotEmpty(message = "Surname is required")
    @field:Size(min = 2, max = 16, message = "Invalid surname")
    val surname: String,

    val role: Role = Role.USER
)
