package com.company.autocontrol.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admin/booking")
class AdminBookingController {

    @GetMapping("/bb")
    fun getBooking(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello, World!")
    }

    @PutMapping
    fun putBooking() {
    }
}
