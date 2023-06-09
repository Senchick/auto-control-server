package com.company.autocontrol.service

import com.company.autocontrol.dto.UserDto
import com.company.autocontrol.entity.UserEntity
import com.company.autocontrol.exception.UserNotFoundException
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
            role = userDto.role,
            department = userDto.department
        )

        userRepository.save(user)
    }

    fun findByLogin(login: String): UserEntity? {
        return userRepository.findByLogin(login)
    }

    fun deleteByLogin(login: String): Boolean {
        return userRepository.deleteByLogin(login) == 0L // true = error
    }

    fun enableByLogin(login: String, enable: Boolean): Boolean {
        val user = findByLogin(login) ?: throw UserNotFoundException()

        user.enabled = enable

        userRepository.save(user)

        return enable
    }
}
