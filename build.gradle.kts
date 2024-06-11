import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.spring.dependency-management")
    kotlin("jvm")
}

group = "ru.skubatko.dev"
version = "1.0.0"
description = "HW for OTUS Architecture and patterns course"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "io.spring.dependency-management")

    configurations.all {
        resolutionStrategy {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }

    dependencies {
        val mockkVersion: String by project
        val sokomishalovCommonsVersion: String by project
        val awaitilityVersion: String by project

        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        implementation("ru.sokomishalov.commons:commons-logging:$sokomishalovCommonsVersion")

        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(module = "junit")
            exclude(module = "mockito-core")
        }
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testImplementation("org.junit.jupiter:junit-jupiter-params")
        testImplementation("org.assertj:assertj-core")
        testImplementation("org.awaitility:awaitility:$awaitilityVersion")
        testImplementation("io.mockk:mockk:$mockkVersion")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}
