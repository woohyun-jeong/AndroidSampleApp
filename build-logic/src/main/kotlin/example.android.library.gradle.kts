import com.example.sampleapp.configureCoroutineAndroid
import com.example.sampleapp.configureHiltAndroid
import com.example.sampleapp.configureKotest
import com.example.sampleapp.configureKotlinAndroid

plugins {
    id("com.android.library")
    id("example.verify.detekt")
}

configureKotlinAndroid()
configureKotest()
configureCoroutineAndroid()
configureHiltAndroid()
