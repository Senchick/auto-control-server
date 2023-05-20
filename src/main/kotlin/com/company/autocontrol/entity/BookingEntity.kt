package com.company.autocontrol.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "booking")
class BookingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "road_section_id")
    val roadSection: RoadSectionEntity,

    @ManyToOne
    @JoinColumn(name = "department_id")
    val department: DepartmentEntity?,

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: UserEntity?,

    @Column(length = 4096)
    val comment: String,

    val fromInterval: LocalDateTime,
    val toInterval: LocalDateTime,

    @Enumerated(EnumType.ORDINAL)
    val bookingType: BookingType
)
