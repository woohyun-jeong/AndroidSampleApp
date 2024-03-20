plugins {
    id("example.android.library")
    id("example.android.compose")
}

android {
    namespace = "com.example.sampleapp.core.designsystem"
}

dependencies {
    implementation(libs.androidx.appcompat)
    
    implementation(libs.landscapist.bom)
    implementation(libs.landscapist.coil)
    implementation(libs.landscapist.placeholder)

    testImplementation(libs.junit4)
    testImplementation(libs.kotlin.test)
}
