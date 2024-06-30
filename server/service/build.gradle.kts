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

    implementation(project(":api"))
    implementation(project(":common"))
    implementation(project(":jwt:client"))
    implementation(project(":hw"))
    implementation(project(":user:client"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-security")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("ru.sokomishalov.commons:commons-spring:$sokomishalovCommonsVersion")

    testImplementation("org.springframework.security:spring-security-test")
}

springBoot {
    buildInfo()
}

application {
    mainClass.set("ru.skubatko.dev.server.ServerApplicationKt")
}
