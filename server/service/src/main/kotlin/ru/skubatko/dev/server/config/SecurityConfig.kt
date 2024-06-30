package ru.skubatko.dev.server.config

import ru.skubatko.dev.jwt.client.JwtClient
import ru.skubatko.dev.server.jwt.JwtTokenFilter
import ru.skubatko.dev.server.service.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsServiceImpl,
    private val jwtClient: JwtClient
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { it.disable() }
            .cors { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/v1/auth/**", "/websocket/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                JwtTokenFilter(jwtClient, userDetailsService),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling { ex ->
                ex.authenticationEntryPoint { _, response, _ ->
                    response.sendError(401, "Unauthorized")
                }
                ex.accessDeniedHandler { _, response, _ ->
                    response.sendError(403, "Forbidden")
                }
            }
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager() =
        ProviderManager(
            DaoAuthenticationProvider(passwordEncoder()).apply {
                setUserDetailsService(userDetailsService)
            })
}
