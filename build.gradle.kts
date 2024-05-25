import ru.skubatko.dev.GenerateAdaptersTask
import ru.skubatko.dev.IoCAdapterGeneratorPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
    id("org.springframework.boot") version "3.2.5" apply false
    id("io.spring.dependency-management") version "1.1.4"
}

group = "ru.skubatko.dev"
version = "1.0.0"
description = "HW for OTUS Architecture and patterns course"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("ch.qos.logback:logback-core")
    implementation("ch.qos.logback:logback-classic")
    implementation("ru.sokomishalov.commons:commons-logging:1.2.1")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core")
    testImplementation("io.mockk:mockk:1.13.10")
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

apply<IoCAdapterGeneratorPlugin>()

tasks.named<GenerateAdaptersTask>("generateAdapters")

tasks.withType<KotlinCompile>().configureEach {
    dependsOn("generateAdapters")
}

afterEvaluate {
    (extensions["sourceSets"] as SourceSetContainer)["main"]
        .java
        .srcDir("src-gen")
}
