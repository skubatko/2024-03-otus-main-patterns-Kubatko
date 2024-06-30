package ru.skubatko.dev.user.web

import ru.skubatko.dev.api.models.user.UserDto
import ru.skubatko.dev.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
) {

    @GetMapping
    fun findByName(@RequestParam(value = "login", required = true) login: String): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.findByLogin(login))
    }
}
