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

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.security:spring-security-crypto")

    runtimeOnly("com.h2database:h2")

    implementation("ru.sokomishalov.commons:commons-spring:$sokomishalovCommonsVersion")
}

springBoot {
    buildInfo()
}

application {
    mainClass.set("ru.skubatko.dev.user.UsersApplicationKt")
}
