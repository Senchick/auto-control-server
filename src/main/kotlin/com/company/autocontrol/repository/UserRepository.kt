package com.company.autocontrol.repository

import com.company.autocontrol.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByLogin(login: String): UserEntity?
}
