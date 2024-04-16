import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    application
    kotlin("jvm")
    id("io.spring.dependency-management")
}

group = "ru.skubatko.dev"
version = "1.0.0"
description = "HW for OTUS Architecture and patterns course"

repositories {
    mavenCentral()
}

val javaVersion: String by project
val springBootBomVersion: String by project

dependencyManagement {
    dependencies {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:${springBootBomVersion}")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
    targetCompatibility = JavaVersion.toVersion(javaVersion)
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
        languageLevel = IdeaLanguageLevel(javaVersion)
    }
}

application {
    mainClass.set("ru.skubatko.dev.KotlinAppKt")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = javaVersion
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}
