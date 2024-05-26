package ru.skubatko.dev

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

open class IoCAdapterGeneratorPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.create("ioCAdapterCodegen", IoCAdapterGeneratorPluginExtension::class.java)
        target.tasks.create("generateAdapters", GenerateAdaptersTask::class.java).run {
            description = "IoC adapters generator plugin"
            group = "generator"
            packageName = extension.packageName
            outputDir = extension.outputDir.asFile
        }
    }
}

abstract class IoCAdapterGeneratorPluginExtension(project: Project) {

    var messageFile: RegularFile
        get() = messageFileProperty.get()
        set(value) = messageFileProperty.set(value)

    var outputDir: Directory
        get() = outputDirProperty.get()
        set(value) = outputDirProperty.set(value)

    var packageName: String
        get() = packageNameProperty.get()
        set(value) = packageNameProperty.set(value)

    private val objects = project.objects

    private val packageNameProperty: Property<String> = objects.property(String::class.java)
        .convention("ru.skubatko.dev.hw06")

    private val messageFileProperty = objects.fileProperty()

    private val outputDirProperty = objects.directoryProperty()
        .convention(project.layout.buildDirectory.dir("generated"))

}

open class GenerateAdaptersTask : DefaultTask() {

    @get:Input
    lateinit var packageName: String

    @get:OutputDirectory
    lateinit var outputDir: File

    @TaskAction
    fun generate() {
        IoCAdapterGenerator().generate(packageName, outputDir)
    }
}
