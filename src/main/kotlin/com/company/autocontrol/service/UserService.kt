package com.company.autocontrol.service

import com.company.autocontrol.enums.Role
import com.company.autocontrol.entity.UserEntity
import com.company.autocontrol.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    fun registerUser(firstname: String, surname: String, login: String, password: String, role: Role) {
        val user = UserEntity(
            id = 0,
            login = login,
            password = passwordEncoder.encode(password),
            firstname = firstname,
            surname = surname,
            role = role
        )

        userRepository.save(user)
    }
}
