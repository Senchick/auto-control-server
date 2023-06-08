package com.company.autocontrol.component

import com.company.autocontrol.dto.error.ErrorCode
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class IntToErrorCodeConverter : Converter<Int, ErrorCode> {
    override fun convert(source: Int): ErrorCode? {
        return ErrorCode.values().find { it.value == source }
    }
}
