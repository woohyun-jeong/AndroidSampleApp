package com.example.sampleapp.core.designsystem.component

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.junit.Assert
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class HTInputCheckTextFieldsView3Test {
    @DisplayName("HTInputCheckTextFieldsView MaxLength 테스트")
    @Test
    fun testHTInputCheckTextFieldsView1() {
        val view = HTInputCheckTextFieldsView(
            image = null,
            textStyle = null,
            maxLength = 2,
            verification = null
        )

        val text = "123"
        val isMaxLength = view.checkMaxLength(text)
        assertFalse(isMaxLength)
    }

    @DisplayName("HTInputCheckTextFieldsView MaxLength 테스트")
    @Test
    fun testHTInputCheckTextFieldsView2() {
        val view = HTInputCheckTextFieldsView(
            image = null,
            textStyle = null,
            maxLength = 10,
            verification = object : HTInputCheckTextFieldsView.Verification<VerifyType> {
                override fun verify(input: String): VerifyType {
                    when(input.length){
                        2 -> {
                            assertTrue(true)
                            println("testHTInputCheckTextFieldsView2 is success")
                        }
                        else -> fail()
                    }

                    return DefaultVerifyType.VerifyOk
                }
            }
        )

        val text = "12"
        view.executeVerification(text)
    }
}