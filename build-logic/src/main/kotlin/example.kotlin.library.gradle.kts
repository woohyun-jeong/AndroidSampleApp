import com.example.sampleapp.configureKotest
import com.example.sampleapp.configureKotlin

plugins {
    kotlin("jvm")
    id("example.verify.detekt")
}

configureKotlin()
configureKotest()
