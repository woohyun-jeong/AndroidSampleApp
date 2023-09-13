import com.example.sampleapp.configureHiltAndroid
import com.example.sampleapp.configureKotlinAndroid

plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureHiltAndroid()
//configureKotestAndroid()
