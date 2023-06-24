package com.company.autocontrol.dto

import com.company.autocontrol.enums.BookingType
import java.time.LocalDateTime

data class BookingDto(
    val date: LocalDateTime,
    val from: LocalDateTime,
    val to: LocalDateTime,
    val userId: Long,
    val roadSectionId: Long,
    val bookingType: BookingType,
    val comment: String
)
