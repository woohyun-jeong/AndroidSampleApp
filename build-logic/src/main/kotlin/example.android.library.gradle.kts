import com.example.sampleapp.configureCoroutineAndroid
import com.example.sampleapp.configureDataAndroid
import com.example.sampleapp.configureHiltAndroid
import com.example.sampleapp.configureKotest
import com.example.sampleapp.configureKotlinAndroid
import com.example.sampleapp.configurePagingAndroid

plugins {
    id("com.android.library")
    id("example.verify.detekt")
}

configureKotlinAndroid()
configureKotest()
configureCoroutineAndroid()
configureHiltAndroid()
configureDataAndroid()
configurePagingAndroid()
