plugins {
    id("org.springframework.boot")
    kotlin("plugin.spring")
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
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("ru.sokomishalov.commons:commons-spring:$sokomishalovCommonsVersion")
}

springBoot {
    buildInfo()
}
