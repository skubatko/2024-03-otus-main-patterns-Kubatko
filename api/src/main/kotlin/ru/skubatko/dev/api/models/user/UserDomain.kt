package ru.skubatko.dev.api.models.user

import ru.sokomishalov.commons.core.consts.EMPTY

@JvmInline
value class Username(private val value: String) {
    fun asString() = value

    companion object {
        val DEFAULT = Username(EMPTY)
    }
}

@JvmInline
value class UserAuthority(private val value: String) {
    fun asString() = value

    companion object {
        val DEFAULT = UserAuthority(EMPTY)
    }
}
