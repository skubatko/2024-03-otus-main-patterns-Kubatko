package ru.skubatko.dev.user.repository

import ru.skubatko.dev.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByLogin(username: String): UserEntity?
}
