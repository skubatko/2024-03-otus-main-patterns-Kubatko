import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.24"
    id("org.springframework.boot") version "3.2.6" apply false
    id("io.spring.dependency-management") version "1.1.5"
}

group = "ru.skubatko.dev"
version = "1.0.0"

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.squareup:kotlinpoet:1.16.0")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.assertj:assertj-core")
    testImplementation("io.mockk:mockk:1.13.10")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

sourceSets.test{
    java.srcDirs("buildSrc/src/test/kotlin")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
