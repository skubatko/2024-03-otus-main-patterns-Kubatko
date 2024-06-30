package ru.skubatko.dev.common.rest

import java.time.Duration

open class RestProps(
    val baseUrl: String = "http://localhost:8080",
    val connectTimeout: Duration = Duration.ofSeconds(15),
    val readTimeout: Duration = Duration.ofSeconds(40),
) {
    companion object {
        val DEFAULT = RestProps()
    }
}
