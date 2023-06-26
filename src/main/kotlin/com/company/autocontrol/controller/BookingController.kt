package com.company.autocontrol.controller

import com.company.autocontrol.dto.BookingDto
import com.company.autocontrol.dto.BookingOutputDto
import com.company.autocontrol.dto.IdDto
import com.company.autocontrol.service.BookingService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/v1/user/booking")
class BookingController {
    @Autowired
    private lateinit var bookingService: BookingService

    @GetMapping("/all")
    fun getAllByDate(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDateTime,
        @RequestParam roadSectionId: Long,
    ): ResponseEntity<List<BookingOutputDto>> {
        return ResponseEntity.ok().body(bookingService.findAllByDateAndByRoadSection(date, roadSectionId))
    }

    @PostMapping("/add")
    fun add(@Valid @RequestBody booking: BookingDto): ResponseEntity<IdDto> {
        val id = bookingService.createBooking(booking)

        return ResponseEntity.ok(IdDto(id))
    }
}
