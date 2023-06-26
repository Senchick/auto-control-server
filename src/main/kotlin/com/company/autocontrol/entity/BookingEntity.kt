package com.company.autocontrol.entity

import com.company.autocontrol.enums.BookingStatus
import com.company.autocontrol.enums.BookingType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "booking")
class BookingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "road_section_id")
    val roadSection: RoadSectionEntity,

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: UserEntity,

    @Column(length = 4096)
    var comment: String,

    var fromInterval: LocalDateTime,
    var toInterval: LocalDateTime,

    val createdDate: LocalDateTime = LocalDateTime.now(),
    var date: LocalDateTime,

    @Enumerated(EnumType.ORDINAL)
    var bookingStatus: BookingStatus,

    @Enumerated(EnumType.ORDINAL)
    val bookingType: BookingType
)
