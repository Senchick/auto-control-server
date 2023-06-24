package com.company.autocontrol.controller

import com.company.autocontrol.entity.RoadSectionEntity
import com.company.autocontrol.service.RoadSectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/user/road-section")
class RoadSectionController {
    @Autowired
    private lateinit var roadSectionService: RoadSectionService

    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<RoadSectionEntity>> {
        return ResponseEntity.ok().body(roadSectionService.findAll())
    }
}
