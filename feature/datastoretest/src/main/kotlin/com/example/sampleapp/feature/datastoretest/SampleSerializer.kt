package com.example.sampleapp.feature.datastoretest

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.sampleapp.Sample
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
object SampleSerializer : Serializer<Sample> {
    override val defaultValue: Sample
        get() = Sample.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Sample {
        try {
            return Sample.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Sample, output: OutputStream) {
        t.writeTo(output)
    }
}