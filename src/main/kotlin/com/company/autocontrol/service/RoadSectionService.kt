package com.company.autocontrol.service

import com.company.autocontrol.entity.RoadSectionEntity
import com.company.autocontrol.exception.RoadSectionConflictException
import com.company.autocontrol.repository.RoadSectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoadSectionService {
    @Autowired
    private lateinit var roadSectionRepository: RoadSectionRepository
    fun findAll(): List<RoadSectionEntity> {
        return roadSectionRepository.findAll()
    }

    fun add(name: String) {
        val roadSection = RoadSectionEntity(name = name)

        if (!roadSectionRepository.existsByName(name)) {
            roadSectionRepository.save(roadSection)
        } else {
            throw RoadSectionConflictException()
        }
    }
}
