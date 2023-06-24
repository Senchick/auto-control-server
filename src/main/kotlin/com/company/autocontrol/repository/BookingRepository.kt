package com.company.autocontrol.repository

import com.company.autocontrol.entity.BookingEntity
import com.company.autocontrol.entity.RoadSectionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface BookingRepository : JpaRepository<BookingEntity, Long> {

    fun findByRoadSectionAndFromIntervalBetween(
        roadSection: RoadSectionEntity,
        start: LocalDateTime,
        end: LocalDateTime
    ): List<BookingEntity>

    fun findByRoadSectionAndFromIntervalLessThanEqualAndToIntervalGreaterThanEqual(
        roadSection: RoadSectionEntity,
        end: LocalDateTime,
        start: LocalDateTime
    ): List<BookingEntity>

    @Query(
        "SELECT b FROM BookingEntity b WHERE b.roadSection.id = :roadSectionId AND " +
            "b.date = :date AND b.bookingStatus = 1 AND " +
            "(b.fromInterval <= :to AND :from < b.toInterval)"
    )
    fun findOverlappingBookings(
        roadSectionId: Long,
        date: LocalDateTime,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<BookingEntity>
}
