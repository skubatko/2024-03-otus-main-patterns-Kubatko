import ru.skubatko.dev.GenerateAdaptersTask
import ru.skubatko.dev.IoCAdapterGeneratorPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") apply false
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    implementation("ch.qos.logback:logback-core")
    implementation("ch.qos.logback:logback-classic")
}

apply<IoCAdapterGeneratorPlugin>()

tasks.named<GenerateAdaptersTask>("generateAdapters")

tasks.withType<KotlinCompile>().configureEach {
    dependsOn("generateAdapters")
}

sourceSets.main {
    java.srcDirs("build/generated/src/main/kotlin")
}
