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
    val mockkVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.mockk:mockk:$mockkVersion")
}
