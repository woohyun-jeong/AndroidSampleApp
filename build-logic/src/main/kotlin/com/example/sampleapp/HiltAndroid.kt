package com.example.sampleapp

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("dagger.hilt.android.plugin")
        apply("org.jetbrains.kotlin.kapt")
    }

    val libs = extensions.libs
    dependencies {
        "implementation"(libs.findLibrary("hilt.android").get())
        "kapt"(libs.findLibrary("hilt.compiler").get())
        "kaptAndroidTest"(libs.findLibrary("hilt.compiler").get())
    }
}
