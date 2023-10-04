plugins {
    id("example.android.library")
}

android {
    namespace = "com.example.sampleapp.core.datastore"
}

dependencies {
    testImplementation(libs.junit4)
    testImplementation(libs.kotlin.test)
    implementation(libs.androidx.datastore)
}
