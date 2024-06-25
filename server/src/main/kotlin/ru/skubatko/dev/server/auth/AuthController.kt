package ru.skubatko.dev.server.auth

import ru.skubatko.dev.api.models.auth.AuthReqDto
import ru.skubatko.dev.api.models.auth.AuthRespDto
import ru.skubatko.dev.server.jwt.JwtTokenUtil
import ru.skubatko.dev.server.user.UserDetailsServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sokomishalov.commons.core.log.Loggable

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val jwtTokenUtil: JwtTokenUtil,
    private val userDetailsService: UserDetailsServiceImpl,
    private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/login")
    fun login(@RequestBody authReqDto: AuthReqDto): ResponseEntity<AuthRespDto> {
        logInfo("Login request received for user: ${authReqDto.username}")
        val authenticate: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authReqDto.username,
                authReqDto.password
            )
        )
        SecurityContextHolder.getContext().authentication = authenticate
        val userDetails = userDetailsService.loadUserByUsername(authReqDto.username)
        val token = jwtTokenUtil.generateToken(userDetails)

        return ResponseEntity.ok(AuthRespDto(token = token, username = userDetails.username))
    }

    companion object : Loggable
}
