@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("example.android.library")
    alias(libs.plugins.protobuf)

}

android {
    namespace = "com.example.sampleapp.core.datastore"
}

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering {
                    option("lite")
                }
                val kotlin by registering {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    testImplementation(libs.junit4)
    testImplementation(libs.kotlin.test)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core)
    api(libs.androidx.datastore.core)
    api(libs.protobuf.kotlin.lite)

}