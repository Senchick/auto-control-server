package com.company.autocontrol.advice

import com.company.autocontrol.dto.error.ErrorResponse
import com.company.autocontrol.exception.RoadSectionConflictException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class RoadSectionExceptionHandler {
    @ExceptionHandler(RoadSectionConflictException::class)
    fun handleRoadSectionConflictException(e: RoadSectionConflictException): ResponseEntity<ErrorResponse<Nothing>> {
        val response = ErrorResponse<Nothing>(message = "RoadSection conflict.")

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response)
    }
}
