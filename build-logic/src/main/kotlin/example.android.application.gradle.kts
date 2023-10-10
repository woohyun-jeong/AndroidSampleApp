import com.example.sampleapp.configureCoilAndroid
import com.example.sampleapp.configureHiltAndroid
import com.example.sampleapp.configureKotestAndroid
import com.example.sampleapp.configureKotlinAndroid
import com.example.sampleapp.libs
import gradle.kotlin.dsl.accessors._80a2ae57395e1362b61311ead0eb480f.implementation

plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureHiltAndroid()
configureKotestAndroid()
configureCoilAndroid()


dependencies {
    implementation(project(":core:model"))
}
