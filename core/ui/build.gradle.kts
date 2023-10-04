plugins {
    id("example.android.library")
    id("example.android.compose")
}

android {
    namespace = "com.example.sampleapp.core.ui"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.designsystem)
}
