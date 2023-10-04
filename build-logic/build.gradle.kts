plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.verify.detektPlugin)
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "example.android.hilt"
            implementationClass = "com.example.sampleapp.HiltAndroidPlugin"
        }
        register("kotlinHilt") {
            id = "example.kotlin.hilt"
            implementationClass = "com.example.sampleapp.HiltKotlinPlugin"
        }
    }
}
