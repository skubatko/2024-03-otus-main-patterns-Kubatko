package ru.skubatko.dev.user.entity

import ru.skubatko.dev.api.models.user.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name = "users")
class UserEntity(

    @Column(name = "name", nullable = true)
    var name: String? = null,

    @Column(name = "login", nullable = true)
    var login: String? = null,

    @Column(name = "password", nullable = true)
    var password: String? = null,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = true)
    var role: UserRole? = null
) : AbstractEntity<Int>(), Serializable
