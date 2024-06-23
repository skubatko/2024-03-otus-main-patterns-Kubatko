package ru.skubatko.dev.server.user

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var name: String? = null
    var login: String? = null
    var pswd: String? = null
    @Enumerated(value = EnumType.STRING)
    var role: UserRole? = null

    override fun getAuthorities() = listOf(SimpleGrantedAuthority(role?.name))

    override fun getUsername(): String? = login

    override fun getPassword(): String? = pswd

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
