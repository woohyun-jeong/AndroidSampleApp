package com.example.sampleapp.core.designsystem.component

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.example.sampleapp.core.designsystem.base.BaseComposeView
import com.example.sampleapp.core.designsystem.base.BaseLogic
import com.example.sampleapp.core.designsystem.base.BaseStyle
import com.example.sampleapp.core.designsystem.base.DefaultVerifyType
import com.example.sampleapp.core.designsystem.base.Verification
import com.example.sampleapp.core.designsystem.base.VerifyType


/**
 * 두 번째 테스트 Verify Type
 */
sealed class DefaultVerifyTypeVersion2 : VerifyType {
    object VerifyOk2 : DefaultVerifyTypeVersion2()
}

/**
 * InputCheckTextFields 관련 Logic Interface
 */
interface InputCheckTextFieldsLogic : BaseLogic {
    fun checkMaxLength(text: String): Boolean

    fun executeVerification(text: String)
}

/**
 * 기본 HTInputCheckTextFieldsStyle
 *
 * @property textStyle
 */
data class HTInputCheckTextFieldsStyle(
    val textStyle: TextStyle? = null,
    val image: HTInputCheckTextFieldsView.Image? = null
) : BaseStyle

/**
 * 첫 번째 테스트  HTInputCheckTextFieldsView
 */
open class HTInputCheckTextFieldsView(
    protected val maxLength: Int = 0,
    protected val verification: Verification<out VerifyType>? = null
) : BaseComposeView,
    InputCheckTextFieldsLogic,
    BaseComposeView.ComposeViewStyle<HTInputCheckTextFieldsStyle> {

    /**
     * HTInputCheckTextFieldsView에 Image를 위한 Data Class
     *
     * @property id
     * @property size
     * @property contentScale
     */
    data class Image(
        @DrawableRes val id: Int, val size: Dp, val contentScale: ContentScale
    )

    @Composable
    override fun OnDraw() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp)
        ) {
            var textRemember by remember { mutableStateOf("") }
            val image = defineStyleType().image

            if (image != null) {
                Image(
                    painterResource(id = image.id),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(image.size),
                    contentScale = image.contentScale
                )
            }

            val startPadding = if (image != null) 20.dp else 0.dp

            TextField(
                value = textRemember,
                onValueChange = { text ->
                    //최대 길이 체크
                    if (!checkMaxLength(text)) {
                        return@TextField
                    }
                    textRemember = text

                    //Input Text 관련 검증 시작
                    executeVerification(text)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = startPadding),
                textStyle = defineStyleType().textStyle ?: LocalTextStyle.current.copy(
                    textAlign = TextAlign.End
                )
            )
        }
    }

    override fun checkMaxLength(text: String): Boolean {
        return text.length <= maxLength
    }

    @Throws(NullPointerException::class)
    override fun executeVerification(text: String) {
        //검증 기능
        val result = verification?.verify(text) ?: throw BaseComposeView.ComposeViewError(
            "Verification", Throwable("HTInputCheckTextFields verification is null")
        )

        when (result) {
            is DefaultVerifyType.VerifyMaxInputTextError -> {
            }

            is DefaultVerifyType.VerifyAlreadyExistTextError -> {
            }

            is DefaultVerifyType.VerifyTextVerifyError -> {
            }

            is DefaultVerifyType.VerifyOk -> {
            }

            else -> throw Verification.VerificationTypeError("HTInputCheckTextFields verify() Result Type is else!")
        }
    }

    override fun defineStyleType(): HTInputCheckTextFieldsStyle {
        return HTInputCheckTextFieldsStyle()
    }

}

/**
 * 첫 번째 HTInputCheckTextFieldsView를 상속받고 VerifyType 추가한 HTInputCheckTextFieldsView2
 */
open class HTInputCheckTextFieldsView2(
    maxLength: Int,
    verification: Verification<VerifyType>? = null
) : HTInputCheckTextFieldsView(
    maxLength = maxLength,
    verification = verification
) {
    @Composable
    override fun OnDraw() {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            var textRemember by remember { mutableStateOf("") }
            val image = defineStyleType().image

            if (image != null) {
                Image(
                    painterResource(id = image.id),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(image.size),
                    contentScale = image.contentScale
                )
            }

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
                            Log.d(
                                "HTInputCheckTextFields2",
                                "verify() DefaultVerifyType.VerifyMaxInputTextError"
                            )
                        }

                        is DefaultVerifyType.VerifyAlreadyExistTextError -> {
                            Log.d(
                                "HTInputCheckTextFields2",
                                "verify() DefaultVerifyType.VerifyAlreadyExistTextError"
                            )
                        }

                        is DefaultVerifyType.VerifyTextVerifyError -> {
                            Log.d(
                                "HTInputCheckTextFields2",
                                "verify() DefaultVerifyType.VerifyTextVerifyError"
                            )
                        }

                        is DefaultVerifyType.VerifyOk -> {
                            Log.d("HTInputCheckTextFields2", "verify() DefaultVerifyType.VerifyOk")
                        }

                        is DefaultVerifyTypeVersion2.VerifyOk2 -> {
                            Log.d(
                                "HTInputCheckTextFields2",
                                "verify() DefaultVerifyTypeVersion2.VerifyOk2"
                            )
                        }

                        else -> throw NullPointerException("HTInputCheckTextFields verify() Result Type is else!")
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textStyle = defineStyleType().textStyle ?: LocalTextStyle.current.copy(
                    textAlign = TextAlign.End
                )
            )
        }
    }
}


/**
 * HTInputCheckTextFieldsView를 상속받고 VerifyType 추가한 HTInputCheckTextFieldsView3
 * HTInputCheckTextFieldsView OnDraw 그대로 상속받음
 */
open class HTInputCheckTextFieldsView3(
    maxLength: Int,
    verification: Verification<VerifyType>? = null
) : HTInputCheckTextFieldsView(
    maxLength = maxLength,
    verification = verification
)