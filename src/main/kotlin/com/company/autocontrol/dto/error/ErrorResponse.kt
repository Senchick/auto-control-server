package com.company.autocontrol.dto.error

data class ErrorResponse<T>(
    val message: String,
    val code: ErrorCode? = null,
    val data: T? = null
)
