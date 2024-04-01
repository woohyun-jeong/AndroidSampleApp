plugins {
    id("example.android.feature")
}

android {
    namespace = "com.example.sampleapp.feature.main"
}

dependencies {
    implementation(project(":feature:pager"))
    implementation(project(":feature:sample"))
    implementation(project(":feature:datastoretest"))
    implementation(project(":feature:sample2"))
    implementation(project(":feature:sample3"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.kotlinx.immutable)
    implementation(files("./libs/library_module-release-1.0.8.aar"))
}
