package com.company.autocontrol.service

import com.company.autocontrol.dto.UserDto
import com.company.autocontrol.entity.UserEntity
import com.company.autocontrol.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    fun registerUser(userDto: UserDto) {
        val user = UserEntity(
            login = userDto.login,
            password = passwordEncoder.encode(userDto.password),
            firstname = userDto.firstname,
            surname = userDto.surname,
            role = userDto.role
        )

        userRepository.save(user)
    }

    fun findByLogin(login: String): UserEntity? {
        return userRepository.findByLogin(login)
    }
}
