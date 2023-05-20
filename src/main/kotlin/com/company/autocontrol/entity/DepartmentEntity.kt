package com.company.autocontrol.entity

import jakarta.persistence.*

@Entity
@Table(name = "department")
class DepartmentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String
)
