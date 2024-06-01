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
    implementation(project(":api"))

    implementation("org.springframework.boot:spring-boot-starter-websocket")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

springBoot {
    buildInfo()
}

application {
    mainClass.set("ru.skubatko.dev.agent.AgentApplicationKt")
}
