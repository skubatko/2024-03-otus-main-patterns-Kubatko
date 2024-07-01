package ru.skubatko.dev.auth.models

import ru.skubatko.dev.api.models.user.UserRole
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class AuthUserDetails(
    val name: String? = null,
    val login: String? = null,
    val pswd: String? = null,
    var role: UserRole? = null
) : UserDetails {

    override fun getAuthorities() = listOf(SimpleGrantedAuthority(role?.name))

    override fun getUsername(): String? = login

    override fun getPassword(): String? = pswd

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
