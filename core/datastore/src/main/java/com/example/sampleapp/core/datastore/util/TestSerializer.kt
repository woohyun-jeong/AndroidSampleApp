package com.example.sampleapp.core.datastore.util

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.sampleapp.core.datastore.TestData
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class TestSerializer @Inject constructor(
    private val crypto: Crypto
) : Serializer<TestData> {
    override val defaultValue: TestData get() = TestData.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TestData {
        return try {
            TestData.parseFrom(
                input
                    .readBytes()
                    .let(crypto::decrypt)
            )
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: TestData, output: OutputStream) {
        output.write(crypto.encrypt(t.toByteArray()))
        output.flush()
    }
}