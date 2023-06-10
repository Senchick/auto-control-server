package com.company.autocontrol.dto.error

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class ErrorCode(val value: Int) {
    UNKNOWN(0),
    VALIDATION(1);

    @JsonValue
    fun toValue(): Int = value

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromValue(value: Int): ErrorCode {
            return ErrorCode.values().find { it.value == value } ?: UNKNOWN
        }
    }
}
