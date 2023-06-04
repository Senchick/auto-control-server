package com.company.autocontrol.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BookingNotFoundException::class)
    fun handleBookingNotFoundException(ex: BookingNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }

    @ExceptionHandler(BookingConflictException::class)
    fun handleBookingConflictException(ex: BookingConflictException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.message)
    }
}
