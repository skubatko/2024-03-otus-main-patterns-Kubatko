package ru.skubatko.dev

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class IoCAdapterGeneratorPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.tasks.create("kotlinTestPlugin", GenerateTask::class.java).run {
            description = "Print test message"
            group = "TestPlugin"
        }
    }
}

class GenerateTask : DefaultTask() {
    @TaskAction
    fun testAction() {
        println("Hello World")
    }
}
