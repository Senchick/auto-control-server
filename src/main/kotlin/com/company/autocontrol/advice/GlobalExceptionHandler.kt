package com.company.autocontrol.advice

import com.company.autocontrol.dto.error.ErrorResponse
import com.company.autocontrol.exception.BookingConflictException
import com.company.autocontrol.exception.BookingNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(BookingNotFoundException::class)
    fun handleBookingNotFoundException(e: BookingNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    }

    @ExceptionHandler(BookingConflictException::class)
    fun handleBookingConflictException(e: BookingConflictException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception): ResponseEntity<ErrorResponse<Nothing>> {
        logger.error("Unhandled exception: ", e)

        val errorResponse = ErrorResponse<Nothing>(
            message = "Internal Server Error"
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
