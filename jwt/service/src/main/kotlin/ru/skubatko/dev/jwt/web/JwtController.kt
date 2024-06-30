package ru.skubatko.dev.jwt.web

import ru.skubatko.dev.api.models.jwt.JwtGenerationReqDto
import ru.skubatko.dev.api.models.jwt.JwtGenerationRespDto
import ru.skubatko.dev.api.models.jwt.JwtUsernameRespDto
import ru.skubatko.dev.api.models.jwt.JwtValidationReqDto
import ru.skubatko.dev.api.models.jwt.JwtValidationRespDto
import ru.skubatko.dev.api.models.user.Username
import ru.skubatko.dev.jwt.mappers.toUser
import ru.skubatko.dev.jwt.service.JwtTokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.sokomishalov.commons.core.log.Loggable

@RestController
@RequestMapping("/api/v1/jwt")
class JwtController(
    private val jwtTokenService: JwtTokenService
) {

    @PostMapping("/generate")
    fun generate(@RequestBody jwtGenerationReqDto: JwtGenerationReqDto) =
        ResponseEntity.ok(JwtGenerationRespDto(jwtTokenService.generateToken(jwtGenerationReqDto.toUser())))

    @GetMapping
    fun getUsername(@RequestParam(value = "token", required = true) token: String) =
        ResponseEntity.ok(JwtUsernameRespDto(jwtTokenService.getUsername(token)))

    @PostMapping("/validate")
    fun isTokenValid(@RequestBody jwtValidationReqDto: JwtValidationReqDto) =
        ResponseEntity.ok(
            JwtValidationRespDto(
                jwtTokenService.isTokenValid(
                    jwtValidationReqDto.token,
                    Username(jwtValidationReqDto.username)
                )
            )
        )

    companion object : Loggable
}
