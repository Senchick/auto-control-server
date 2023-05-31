package com.company.autocontrol.entity

import com.company.autocontrol.enums.Role
import jakarta.persistence.*
import org.hibernate.validator.constraints.Length

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Length(min = 2, max = 16)
    @Column(length = 16)
    val firstname: String,

    @Length(min = 2, max = 16)
    @Column(length = 16)
    val surname: String,

    @Length(min = 4, max = 32)
    @Column(length = 32)
    val login: String,

    @Length(min = 8, max = 32)
    @Column(length = 32)
    val password: String,

    @Enumerated(EnumType.STRING)
    val role: Role
)
