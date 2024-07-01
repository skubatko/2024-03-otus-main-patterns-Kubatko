package ru.skubatko.dev.server.security

import ru.skubatko.dev.api.models.jwt.JwtValidationReqDto
import ru.skubatko.dev.jwt.client.JwtClient
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.stereotype.Component

@Component
class ServerAuthenticationManager(
    private val jwtClient: JwtClient,
) : AuthenticationManager {

    override fun authenticate(authentication: Authentication): Authentication {
        val token = (authentication as BearerTokenAuthenticationToken).token
        authentication.isAuthenticated =
            jwtClient.isTokenValid(JwtValidationReqDto(token, authentication.name))?.isValid == true
        return authentication
    }
}
