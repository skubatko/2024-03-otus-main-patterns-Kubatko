rootProject.name = "2024-03-otus-main-patterns-Kubatko"

include(
    "agent",
    "api",
    "auth", "auth:client", "auth:service",
    "common",
    "hw",
    "jwt", "jwt:client", "jwt:service",
    "server", "server:client", "server:service",
    "user", "user:client", "user:service"
)

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val springBootVersion: String by settings
        val springDependencyVersion: String by settings

        id("org.springframework.boot") version springBootVersion apply false
        id("io.spring.dependency-management") version springDependencyVersion apply false
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.spring") version kotlinVersion apply false
        kotlin("kapt") version kotlinVersion apply false
    }
}
