package com.company.autocontrol.repository;

import com.company.autocontrol.entity.RoadSectionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoadSectionRepository : JpaRepository<RoadSectionEntity, Long> {
    fun existsByName(name: String): Boolean
}