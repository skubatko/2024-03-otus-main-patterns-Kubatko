package ru.skubatko.dev.hw04.domain

interface UObject {
    fun getProperty(key: String): Any?
    fun setProperty(key: String, newValue: Any)
}
