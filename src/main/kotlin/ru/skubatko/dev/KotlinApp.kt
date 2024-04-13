package ru.skubatko.dev

fun main() {
    println(KotlinApp().greeting)
}

class KotlinApp {
    val greeting: String
        get() {
            return "Hello Kotlin!"
        }

    fun sum(a: Int, b: Int): Int {
        return a + b
    }
}
