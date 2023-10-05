plugins {
    id("example.android.feature")
}

android {
    namespace = "com.example.sampleapp.feature.main"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.kotlinx.immutable)
    implementation(files("./libs/library_module-release-1.0.8.aar"))
}
