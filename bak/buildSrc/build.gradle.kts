plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    runtimeOnly("org.jetbrains.kotlin:kotlin-gradle-plugin")
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation(gradleApi())
}
