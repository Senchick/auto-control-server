package com.company.autocontrol.security

import com.company.autocontrol.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl : UserDetailsService {
    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByLogin(username) ?: throw UsernameNotFoundException("User not found")

        return UserDetailsImpl(user)
    }
}
