package com.company.autocontrol.controller

import com.company.autocontrol.dto.UserDto
import com.company.autocontrol.exception.UserDeleteException
import com.company.autocontrol.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/admin/users")
class AdminUserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/create")
    fun createUser(@Valid @RequestBody user: UserDto): ResponseEntity<Nothing> {
        userService.registerUser(user)

        return ResponseEntity.ok(null)
    }

    @DeleteMapping("/delete/{login}")
    fun deleteUser(@PathVariable login: String): ResponseEntity<Nothing> {
        if (userService.deleteByLogin(login)) {
            throw UserDeleteException()
        }

        return ResponseEntity.ok(null)
    }

    @GetMapping("/enable/{login}/enable/{enabled}")
    fun enableUser(@PathVariable login: String, @PathVariable enabled: Boolean): ResponseEntity<Nothing> {
        userService.enableByLogin(login, enabled)

        return ResponseEntity.ok(null)
    }
}
