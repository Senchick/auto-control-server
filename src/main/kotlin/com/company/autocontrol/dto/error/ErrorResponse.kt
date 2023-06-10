package com.company.autocontrol.dto.error

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse<T>(
    val message: String,
    val code: ErrorCode? = null,
    val data: T? = null
)
