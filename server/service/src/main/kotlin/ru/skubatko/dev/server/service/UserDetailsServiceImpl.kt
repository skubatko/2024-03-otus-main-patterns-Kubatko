package ru.skubatko.dev.server.service

import ru.skubatko.dev.server.mappers.toUserDetails
import ru.skubatko.dev.user.client.UserClient
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.consts.EMPTY

@Service
class UserDetailsServiceImpl(
    private val userClient: UserClient
) : UserDetailsService {

    override fun loadUserByUsername(username: String?) =
        userClient.findByLogin(username ?: EMPTY)
            ?.toUserDetails()
            ?: throw UsernameNotFoundException("User not found")
}
