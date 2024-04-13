pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val dependencyManagementVersion: String by settings

        kotlin("jvm") version kotlinVersion apply false
        id("io.spring.dependency-management") version dependencyManagementVersion apply false
    }
}

rootProject.name = "2024-03-otus-main-patterns-Kubatko"
