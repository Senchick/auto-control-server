package com.company.autocontrol.advice

import com.company.autocontrol.dto.error.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception): ResponseEntity<ErrorResponse<Nothing>> {
        logger.error("Unhandled exception: ", e)

        val errorResponse = ErrorResponse<Nothing>(
            message = "Internal Server Error"
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }

    @ExceptionHandler(BadCredentialsException::class, InsufficientAuthenticationException::class)
    fun handleBadCredentialsException(e: Exception): ResponseEntity<ErrorResponse<Nothing>> {
        logger.error("BadCredentialsException: ", e)

        val errorResponse = ErrorResponse<Nothing>(
            message = "Bad Credentials"
        )

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }
}
