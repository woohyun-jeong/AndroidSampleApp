import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("example.android.library")
    id("example.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.sampleapp.core.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.datastore)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    testImplementation(libs.turbine)
    testImplementation(libs.junit4)
    testImplementation(libs.kotlin.test)
}
