package com.company.autocontrol.service

import com.company.autocontrol.dto.BookingDto
import com.company.autocontrol.dto.BookingOutputDto
import com.company.autocontrol.dto.UserInfoDto
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
import org.springframework.security.core.context.SecurityContextHolder
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
    fun findAllByDateAndByRoadSection(
        date: LocalDateTime,
        roadSectionId: Long
    ): List<BookingOutputDto> = bookingRepository.findAllByDateAndRoadSectionId(date.roundToDay(), roadSectionId).map {
        BookingOutputDto(
            id = it.id!!,
            date = it.date,
            from = it.fromInterval,
            to = it.toInterval,
            roadSectionId = it.roadSection.id!!,
            bookingType = it.bookingType,
            bookingStatus = it.bookingStatus,
            createdDate = it.createdDate,
            comment = it.comment,
            author = with(it.author) {
                UserInfoDto(
                    id = id!!,
                    login = login,
                    firstname = firstname,
                    surname = surname,
                    department = department,
                    role = role
                )
            }
        )
    }
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
        val minute = if (time.minute < HALF_OF_HOUR) 0 else HALF_OF_HOUR
        return time.withMinute(minute).withSecond(0).withNano(0)
    }

    private fun LocalDateTime.roundToDay(): LocalDateTime = withHour(0)
        .withMinute(0)
        .withSecond(0)
        .withNano(0)

    fun createBooking(dto: BookingDto): Long {
        val login = SecurityContextHolder.getContext().authentication.name

        val user = userRepository.findByLogin(login) ?: throw UserNotFoundException()

        val roadSection = roadSectionRepository.findById(dto.roadSectionId).orElseThrow {
            throw RoadSectionNotFoundException()
        }

        if (user.role == Role.USER && dto.bookingType == BookingType.CLOSE) {
            throw BookingConflictException("With the USER role, you cannot create CLOSE")
        }

        val fromRounded = roundToHalfHour(dto.from)
        val toRounded = roundToHalfHour(dto.to)
        val dateRounded = dto.date.roundToDay()

        if (fromRounded.isBefore(fromRounded.withHour(6).withMinute(0)) ||
            toRounded.isAfter(toRounded.withHour(23).withMinute(0))
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

        if (dto.bookingType == BookingType.MONO && overlappingBookings.any {
                it.bookingType == BookingType.MONO && it.bookingStatus == BookingStatus.WAITING
            }
        ) {
            throw BookingConflictException(
                "Cannot create a mono booking as there is already a booking in this interval"
            )
        }

        if (overlappingBookings.any {
                it.author.id == user.id && it.bookingStatus == BookingStatus.APPROVED
            }
        ) {
            throw BookingConflictException("Cannot create a general booking from the same author in this interval")
        }

        val existingWaitingBooking = overlappingBookings.firstOrNull {
            it.bookingStatus == BookingStatus.WAITING &&
                it.author.id == user.id &&
                it.bookingType == dto.bookingType
        }

        if (existingWaitingBooking != null) {
            existingWaitingBooking.comment = dto.comment
            existingWaitingBooking.fromInterval = fromRounded
            existingWaitingBooking.toInterval = toRounded
            existingWaitingBooking.date = dateRounded

            return bookingRepository.save(existingWaitingBooking).id!!
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

            return bookingRepository.save(newBooking).id!!
        }
    }

    companion object {
        const val HALF_OF_HOUR = 30
    }
}
