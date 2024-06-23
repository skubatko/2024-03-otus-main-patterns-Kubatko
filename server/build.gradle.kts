plugins {
    id("org.springframework.boot")
    kotlin("plugin.spring")
    kotlin("kapt")
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
    implementation(project(":hw"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("com.h2database:h2")

    implementation("ru.sokomishalov.commons:commons-spring:$sokomishalovCommonsVersion")
    implementation("com.auth0:java-jwt:$javaJwtVersion")

    testImplementation("org.springframework.security:spring-security-test")
}

springBoot {
    buildInfo()
}

application {
    mainClass.set("ru.skubatko.dev.server.ServerApplicationKt")
}
