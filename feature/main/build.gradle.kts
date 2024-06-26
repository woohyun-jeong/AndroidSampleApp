plugins {
    id("example.android.feature")
}

android {
    namespace = "com.example.sampleapp.feature.main"
}

dependencies {
    implementation(project(":feature:pager"))
    implementation(project(":feature:sample"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.kotlinx.immutable)
}
