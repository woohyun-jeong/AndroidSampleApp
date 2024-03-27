plugins {
    id("example.android.feature")
}

android {
    namespace = "com.example.sampleapp.feature.sample"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(projects.core.designsystem)
    implementation(libs.kotlinx.immutable)
}