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
    implementation(project(":auth:client"))
    implementation(project(":common"))
    implementation(project(":hw"))
    implementation(project(":server:client"))

    implementation("org.springframework.boot:spring-boot-starter-websocket")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("ru.sokomishalov.commons:commons-spring:$sokomishalovCommonsVersion")
}

springBoot {
    buildInfo()
}

application {
    mainClass.set("ru.skubatko.dev.agent.AgentApplicationKt")
}
