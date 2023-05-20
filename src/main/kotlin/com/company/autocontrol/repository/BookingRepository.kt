package com.company.autocontrol.repository

import com.company.autocontrol.entity.BookingEntity
import com.company.autocontrol.entity.RoadSectionEntity
import org.springframework.data.jpa.repository.JpaRepository
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
}
