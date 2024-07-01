package ru.skubatko.dev.auth.web

import ru.skubatko.dev.api.models.auth.AuthReqDto
import ru.skubatko.dev.api.models.auth.AuthRespDto
import ru.skubatko.dev.api.models.jwt.JwtGenerationReqDto
import ru.skubatko.dev.jwt.client.JwtClient
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sokomishalov.commons.core.log.Loggable

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userDetailsService: UserDetailsService,
    private val jwtClient: JwtClient,
    private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/login")
    fun login(@RequestBody authReqDto: AuthReqDto): ResponseEntity<AuthRespDto> {
        logInfo("Login request received for user: ${authReqDto.login}")
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authReqDto.login,
                authReqDto.password
            )
        )
        val userDetails = userDetailsService.loadUserByUsername(authReqDto.login)
        val token = jwtClient.generate(
            JwtGenerationReqDto(userDetails.username, userDetails.authorities.first().authority)
        )?.token

        return ResponseEntity.ok(AuthRespDto(token = token, username = userDetails.username))
    }

    companion object : Loggable
}
