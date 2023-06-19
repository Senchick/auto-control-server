package com.company.autocontrol.controller

import com.company.autocontrol.dto.UserDto
import com.company.autocontrol.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admin/users")
class AdminUserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/create")
    fun createUser(@Valid @RequestBody user: UserDto): ResponseEntity<Any> {
        userService.registerUser(user)

        return ResponseEntity.ok(null)
    }
}
