package com.company.autocontrol.advice

import com.company.autocontrol.dto.error.ErrorResponse
import com.company.autocontrol.exception.BookingConflictException
import com.company.autocontrol.exception.BookingNotFoundException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class BookingExceptionHandler {
    @ExceptionHandler(BookingNotFoundException::class)
    fun handleBookingNotFoundException(e: BookingNotFoundException): ResponseEntity<ErrorResponse<Nothing>> {
        val response = ErrorResponse<Nothing>(message = "Booking not found.")

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @ExceptionHandler(BookingConflictException::class)
    fun handleBookingConflictException(e: BookingConflictException): ResponseEntity<ErrorResponse<Nothing>> {
        val response = ErrorResponse<Nothing>(message = "Booking conflict.")

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response)
    }
}
