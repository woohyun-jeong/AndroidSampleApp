import com.example.sampleapp.configureCoilAndroid
import com.example.sampleapp.configureHiltAndroid
import com.example.sampleapp.configureKotestAndroid
import com.example.sampleapp.configureKotlinAndroid

plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureHiltAndroid()
configureKotestAndroid()
configureCoilAndroid()


dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))
}
