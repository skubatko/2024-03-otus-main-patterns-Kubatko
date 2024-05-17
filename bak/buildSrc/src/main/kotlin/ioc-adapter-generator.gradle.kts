plugins {
//    id("java-library")
//    `java-gradle-plugin`
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}
