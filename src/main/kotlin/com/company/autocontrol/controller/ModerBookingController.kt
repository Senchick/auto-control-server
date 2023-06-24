package com.company.autocontrol.controller

import com.company.autocontrol.service.BookingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/moder/booking")
class ModerBookingController {
    @Autowired
    lateinit var bookingService: BookingService

    @GetMapping("/{id}/approve")
    fun approve(@PathVariable id: Long) {
        bookingService.approve(id)
    }

    @GetMapping("/{id}/disapprove")
    fun disapprove(@PathVariable id: Long) {
        bookingService.disapprove(id)
    }
}
