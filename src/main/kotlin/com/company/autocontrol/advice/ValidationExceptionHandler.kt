package com.company.autocontrol.advice

import com.company.autocontrol.dto.error.ErrorCode
import com.company.autocontrol.dto.error.ErrorField
import com.company.autocontrol.dto.error.ErrorResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse<List<ErrorField>>> {
        e.printStackTrace()
        val fields = e.bindingResult.fieldErrors.map {
            ErrorField(it.field, it.defaultMessage)
        }
        val errorResponse = ErrorResponse(
            message = "Validation error",
            code = ErrorCode.VALIDATION,
            data = fields
        )

        return ResponseEntity.badRequest()
            .body(errorResponse)
    }
}
