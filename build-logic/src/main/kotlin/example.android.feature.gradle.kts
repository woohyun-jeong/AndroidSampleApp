import com.example.sampleapp.configureHiltAndroid
import com.example.sampleapp.configurePagingAndroid
import com.example.sampleapp.libs

plugins {
    id("example.android.library")
    id("example.android.compose")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

configureHiltAndroid()
configurePagingAndroid()

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))

    val libs = project.extensions.libs
    implementation(libs.findLibrary("hilt.navigation.compose").get())
    implementation(libs.findLibrary("androidx.compose.navigation").get())
    androidTestImplementation(libs.findLibrary("androidx.compose.navigation.test").get())

    implementation(libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
    implementation(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
    implementation(libs.findLibrary("compose.coil").get())
    implementation(libs.findLibrary("compose.paging").get())
}
