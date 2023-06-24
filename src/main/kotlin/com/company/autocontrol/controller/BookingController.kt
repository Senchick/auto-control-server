package com.company.autocontrol.controller

import com.company.autocontrol.dto.BookingDto
import com.company.autocontrol.entity.BookingEntity
import com.company.autocontrol.service.BookingService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/user/booking")
class BookingController {
    @Autowired
    private lateinit var bookingService: BookingService

    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<BookingEntity>> {
        return ResponseEntity.ok().body(bookingService.findAll())
    }

    @PostMapping("/add")
    fun add(@Valid @RequestBody booking: BookingDto): ResponseEntity<Nothing> {
        bookingService.createBooking(booking)

        return ResponseEntity.ok(null)
    }
}
