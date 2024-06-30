package ru.skubatko.dev.server.jwt

import ru.skubatko.dev.api.models.jwt.JwtValidationReqDto
import ru.skubatko.dev.jwt.client.JwtClient
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.sokomishalov.commons.core.log.Loggable

@Component
class JwtTokenFilter(
    private val jwtClient: JwtClient,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader(AUTHORIZATION)
        logInfo { "Authorization header: $authorizationHeader" }

        var jwtToken: String? = null
        var username: String? = null
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            jwtToken = authorizationHeader.substring(BEARER.length)
            username = jwtClient.getUsername(jwtToken)?.username
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            logInfo("Security context was null, so authorizing user")
            logInfo("User details request received for user: $username")
            val userDetails = userDetailsService.loadUserByUsername(username)
            val isValid = jwtClient.isTokenValid(JwtValidationReqDto(jwtToken, userDetails.username))?.isValid
            if (isValid == true) {
                val usernamePasswordAuthenticationToken =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }

        filterChain.doFilter(request, response)
    }

    companion object : Loggable {
        private const val BEARER = "Bearer "
    }
}
