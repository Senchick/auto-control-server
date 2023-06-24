package com.company.autocontrol.service

import com.company.autocontrol.dto.BookingDto
import com.company.autocontrol.entity.BookingEntity
import com.company.autocontrol.enums.BookingStatus
import com.company.autocontrol.enums.BookingType
import com.company.autocontrol.enums.Role
import com.company.autocontrol.exception.BookingConflictException
import com.company.autocontrol.exception.BookingNotFoundException
import com.company.autocontrol.exception.RoadSectionNotFoundException
import com.company.autocontrol.exception.UserNotFoundException
import com.company.autocontrol.repository.BookingRepository
import com.company.autocontrol.repository.RoadSectionRepository
import com.company.autocontrol.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BookingService {
    @Autowired
    private lateinit var bookingRepository: BookingRepository

    @Autowired
    private lateinit var roadSectionRepository: RoadSectionRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    fun findAll(): List<BookingEntity> = bookingRepository.findAll()
    fun approve(id: Long) {
        val booking = bookingRepository.findById(id)
            .orElseThrow { BookingNotFoundException() }

        booking.bookingStatus = BookingStatus.APPROVED

        bookingRepository.save(booking)
    }

    fun disapprove(id: Long) {
        val booking = bookingRepository.findById(id)
            .orElseThrow { BookingNotFoundException() }

        booking.bookingStatus = BookingStatus.DISAPPROVED

        bookingRepository.save(booking)
    }

    private fun roundToHalfHour(time: LocalDateTime): LocalDateTime {
        val minute = if (time.minute < 30) 0 else 30
        return time.withMinute(minute).withSecond(0).withNano(0)
    }

    fun createBooking(dto: BookingDto) {
        val user = userRepository.findById(dto.userId).orElseThrow { throw UserNotFoundException() }
        val roadSection = roadSectionRepository.findById(dto.roadSectionId).orElseThrow {
            throw RoadSectionNotFoundException()
        }

        if (user.role == Role.USER && dto.bookingType == BookingType.CLOSE) {
            throw BookingConflictException("with the USER role, you cannot create CLOSE")
        }

        val fromRounded = roundToHalfHour(dto.from)
        val toRounded = roundToHalfHour(dto.to)
        val dateRounded = dto.date
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)

        if (fromRounded.isBefore(dto.date.withHour(6).withMinute(0)) ||
            toRounded.isAfter(dto.date.withHour(23).withMinute(0))
        ) {
            throw BookingConflictException("Booking time must be between 6:00 and 23:00")
        }

        val overlappingBookings = bookingRepository.findOverlappingBookings(
            dto.roadSectionId,
            dateRounded,
            fromRounded,
            toRounded
        )

        if (overlappingBookings.any { it.bookingType == BookingType.CLOSE }) {
            throw BookingConflictException("Cannot create a booking as there is a closed booking in this interval")
        }

        if (dto.bookingType == BookingType.MONO && overlappingBookings.isNotEmpty()) {
            throw BookingConflictException(
                "Cannot create a mono booking as there is already a booking in this interval"
            )
        }

        if (dto.bookingType == BookingType.GENERAL && overlappingBookings.any { it.author?.id == dto.userId }) {
            throw BookingConflictException("Cannot create a general booking from the same author in this interval")
        }

        val existingWaitingBooking = overlappingBookings.firstOrNull {
            it.bookingStatus == BookingStatus.WAITING &&
                it.author?.id == dto.userId &&
                it.bookingType == dto.bookingType
        }

        if (existingWaitingBooking != null) {
            existingWaitingBooking.comment = dto.comment
            existingWaitingBooking.fromInterval = fromRounded
            existingWaitingBooking.toInterval = toRounded
            existingWaitingBooking.date = dateRounded

            bookingRepository.save(existingWaitingBooking)
        } else {
            val newBooking = BookingEntity(
                roadSection = roadSection,
                author = user,
                comment = dto.comment,
                date = dateRounded,
                fromInterval = fromRounded,
                toInterval = toRounded,
                bookingStatus = BookingStatus.WAITING,
                bookingType = dto.bookingType
            )

            bookingRepository.save(newBooking)
        }
    }
}
