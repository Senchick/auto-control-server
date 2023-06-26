package com.company.autocontrol.dto

import com.company.autocontrol.enums.BookingStatus
import com.company.autocontrol.enums.BookingType
import java.time.LocalDateTime

class BookingOutputDto(
    val id: Long,
    val date: LocalDateTime,
    val createdDate: LocalDateTime,
    val from: LocalDateTime,
    val to: LocalDateTime,
    val roadSectionId: Long,
    val bookingType: BookingType,
    val bookingStatus: BookingStatus,
    val comment: String,
    val author: UserInfoDto
)
