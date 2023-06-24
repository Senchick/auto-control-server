package com.company.autocontrol.controller

import com.company.autocontrol.dto.UserInfoDto
import com.company.autocontrol.exception.UserNotFoundException
import com.company.autocontrol.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/user")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/info")
    fun info(): ResponseEntity<UserInfoDto> {
        val login = SecurityContextHolder.getContext().authentication.name
        val user = userService.findByLogin(login) ?: throw UserNotFoundException()
        val dto = UserInfoDto(
            id = user.id!!,
            login = user.login,
            firstname = user.firstname,
            surname = user.surname,
            department = user.department,
            role = user.role
        )

        return ResponseEntity.ok(dto)
    }
}
