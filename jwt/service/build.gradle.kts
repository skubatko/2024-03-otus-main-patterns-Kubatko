plugins {
    id("org.springframework.boot")
    kotlin("plugin.spring")
    application
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    val sokomishalovCommonsVersion: String by project
    val javaJwtVersion: String by project

    implementation(project(":api"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("com.auth0:java-jwt:$javaJwtVersion")
    implementation("ru.sokomishalov.commons:commons-spring:$sokomishalovCommonsVersion")
}

springBoot {
    buildInfo()
}

application {
    mainClass.set("ru.skubatko.dev.jwt.JwtApplicationKt")
}
