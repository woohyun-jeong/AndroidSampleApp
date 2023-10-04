plugins {
    id("example.android.library")
}

android {
    namespace = "com.example.sampleapp.core.domain"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)

    implementation(libs.inject)
}
