package com.example.sampleapp.core.designsystem.component

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 유연한 상속을 위해서 interface를 사용함
 */
interface VerifyType

/**
 * 첫 번째 테스트 Verify Type
 */
sealed class DefaultVerifyType : VerifyType {
    object VerifyMaxInputTextError : DefaultVerifyType()
    object VerifyAlreadyExistTextError : DefaultVerifyType()
    object VerifyTextVerifyError : DefaultVerifyType()
    object VerifyOk : DefaultVerifyType()
}

/**
 * 두 번째 테스트 Verify Type
 */
sealed class DefaultVerifyTypeVersion2 : VerifyType {
    object VerifyOk2 : DefaultVerifyTypeVersion2()
}

/**
 * 첫 번째 테스트  HTInputCheckTextFieldsView
 */
open class HTInputCheckTextFieldsView(
    protected val image: Image,
    protected val textStyle: TextStyle? = null,
    protected val maxLength: Int,
    protected val verification: Verification<out VerifyType>? = null
) : BaseComposeView {

    interface Verification<type : VerifyType> {
        fun verify(input: String): type
    }

    data class Image(
        @DrawableRes val id: Int,
        val size: Dp,
        val contentScale: ContentScale
    )

    @Composable
    override fun OnDraw() {
        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            var textRemember by remember { mutableStateOf("") }

            Image(
                painterResource(id = image.id),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(image.size),
                contentScale = image.contentScale
            )
            TextField(
                value = textRemember,
                onValueChange = { text ->
                    if (text.length <= maxLength) {
                        textRemember = text
                    } else {
                        return@TextField
                    }

                    //검증 기능
                    val result = verification?.verify(text) ?: return@TextField
                    when (result) {
                        is DefaultVerifyType.VerifyMaxInputTextError -> {
                            Log.d("HTInputCheckTextFields", "verify() DefaultVerifyType.VerifyMaxInputTextError")
                        }
                        is DefaultVerifyType.VerifyAlreadyExistTextError -> {
                            Log.d("HTInputCheckTextFields", "verify() DefaultVerifyType.VerifyAlreadyExistTextError")
                        }
                        is DefaultVerifyType.VerifyTextVerifyError -> {
                            Log.d("HTInputCheckTextFields", "verify() DefaultVerifyType.VerifyTextVerifyError")
                        }
                        is DefaultVerifyType.VerifyOk -> {
                            Log.d("HTInputCheckTextFields", "verify() DefaultVerifyType.VerifyOk")
                        }
                        else -> throw NullPointerException("HTInputCheckTextFields verify() Result Type is else!")
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                textStyle = textStyle ?: LocalTextStyle.current.copy(textAlign = TextAlign.End)
            )
        }
    }

}

/**
 * 첫 번째 HTInputCheckTextFieldsView를 상속받고 VerifyType 추가한 HTInputCheckTextFieldsView2
 */
open class HTInputCheckTextFieldsView2(
    image: Image,
    textStyle: TextStyle? = null,
    maxLength: Int,
    verification: Verification<VerifyType>? = null
) : HTInputCheckTextFieldsView(
    image = image,
    textStyle = textStyle,
    maxLength = maxLength,
    verification = verification
) {
    @Composable
    override fun OnDraw() {
        Row(Modifier.fillMaxWidth().wrapContentHeight()) {
            var textRemember by remember { mutableStateOf("") }

            Image(
                painterResource(id = image.id),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(image.size),
                contentScale = image.contentScale
            )
            TextField(
                value = textRemember,
                onValueChange = { text ->
                    if (text.length <= maxLength) {
                        textRemember = text
                    } else {
                        return@TextField
                    }

                    //검증 기능
                    val result = verification?.verify(text) ?: return@TextField
                    when (result) {
                        is DefaultVerifyType.VerifyMaxInputTextError -> {
                            Log.d("HTInputCheckTextFields2", "verify() DefaultVerifyType.VerifyMaxInputTextError")
                        }
                        is DefaultVerifyType.VerifyAlreadyExistTextError -> {
                            Log.d("HTInputCheckTextFields2", "verify() DefaultVerifyType.VerifyAlreadyExistTextError")
                        }
                        is DefaultVerifyType.VerifyTextVerifyError -> {
                            Log.d("HTInputCheckTextFields2", "verify() DefaultVerifyType.VerifyTextVerifyError")
                        }
                        is DefaultVerifyType.VerifyOk -> {
                            Log.d("HTInputCheckTextFields2", "verify() DefaultVerifyType.VerifyOk")
                        }
                        is DefaultVerifyTypeVersion2.VerifyOk2 -> {
                            Log.d("HTInputCheckTextFields2", "verify() DefaultVerifyTypeVersion2.VerifyOk2")
                        }
                        else -> throw NullPointerException("HTInputCheckTextFields verify() Result Type is else!")
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                textStyle = textStyle ?: LocalTextStyle.current.copy(textAlign = TextAlign.End)
            )
        }
    }
}