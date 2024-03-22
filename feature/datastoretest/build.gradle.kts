plugins {
    id("example.android.feature")
}

android {
    namespace = "com.example.sampleapp.feature.datastoretest"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.kotlinx.immutable)
    implementation(projects.core.datastore)
    implementation(libs.datastore.core)
    implementation(libs.androidx.datastore.core)
    implementation(libs.protobuf.kotlin.lite)

}