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

    implementation(project(":api"))
    implementation(project(":hw"))

    implementation("org.springframework.boot:spring-boot-starter-websocket")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation("ru.sokomishalov.commons:commons-spring:$sokomishalovCommonsVersion")
}

springBoot {
    buildInfo()
}

application {
    mainClass.set("ru.skubatko.dev.agent.AgentApplicationKt")
}
