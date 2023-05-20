package com.company.autocontrol.entity

import jakarta.persistence.*

@Entity
@Table(name = "road_section")
class RoadSectionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String
)