package com.company.autocontrol.controller

import com.company.autocontrol.service.RoadSectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admin/road-section")
class AdminSectionController {
    @Autowired
    private lateinit var roadSectionService: RoadSectionService

    @PutMapping("/{name}/add")
    fun add(@PathVariable name: String): ResponseEntity<Nothing> {
        roadSectionService.add(name)

        return ResponseEntity.ok().body(null)
    }
}
