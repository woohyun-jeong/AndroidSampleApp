package com.example.sampleapp.core.designsystem.component

import android.content.Context
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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


abstract class HTInputCheckTextFieldsViewTest<VerifyType : BaseInputCheckTextFields.DefaultVerifyType>(
    private val image: Image,
    private val textStyle: TextStyle? = null,
    private val maxLength: Int
) : BaseComposeView {
    abstract fun verify(input: String): VerifyType

    data class Image(
        @DrawableRes val id: Int,
        val size: Dp,
        val contentScale: ContentScale
    )

    @Composable
    override fun OnDraw() {
        Row {
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

                    //정리 기능
                    val result = verify(text)
                    when (result) {
                        BaseInputCheckTextFields.DefaultVerifyType.MaxInputTextError -> TODO()
                        BaseInputCheckTextFields.DefaultVerifyType.AlreadyExistTextError -> TODO()
                        BaseInputCheckTextFields.DefaultVerifyType.TextVerifyError -> TODO()
                        BaseInputCheckTextFields.DefaultVerifyType.Ok -> TODO()
                    }
                },
                singleLine = true,
                modifier = Modifier.wrapContentSize(),
                textStyle = textStyle ?: LocalTextStyle.current.copy(textAlign = TextAlign.End)
            )
        }
    }

}

class HTInputCheckTextFields(context: Context) :
    BaseInputCheckTextFields,
    BaseInputCheckTextFields.Style,
    View(context) {
    private var textField: EditText? = null
    private var activeIndicator: View? = null
    private var trailingIcon: ImageView? = null

    private var initView = false
    private var maxLength = 0

    private val filterHashMap: HashMap<String, InputFilter> = HashMap()

    override fun style(
        @IdRes id: Int,
        defaultView: BaseInputCheckTextFields.DefaultView
    ): BaseInputCheckTextFields {
        runCatching {
            setId(id)
            activeIndicator = findViewById(defaultView.activeIndicatorId)
            textField = findViewById(defaultView.textFieldId)
            trailingIcon = findViewById(defaultView.trailingIconId)
        }.onSuccess {
            Log.d("HTInputCheckTextFields", "style init success!")
            initView = true
        }.onFailure { error ->
            Log.e("HTInputCheckTextFields", "style init failure!", error)
            initView = false
        }
        return this@HTInputCheckTextFields
    }

    override fun style(id: Int): BaseInputCheckTextFields {
        //TODO 적용 가능하면 해야함
        return this@HTInputCheckTextFields
    }

    override fun <VerifyType : BaseInputCheckTextFields.DefaultVerifyType> verify(check: (input: String) -> VerifyType): BaseInputCheckTextFields {
        if (!initView) {
            throw NullPointerException("initView Failure Error!")
        }

        filterHashMap["verify"] = InputFilter { source, start, end, dest, dstart, dend ->
            check.invoke(source.toString())
            return@InputFilter source
        }

        return this@HTInputCheckTextFields
    }

    override fun maxLength(length: Int): BaseInputCheckTextFields {
        if (!initView) {
            throw NullPointerException("initView Failure Error!")
        }

        runCatching {
            maxLength = length
            filterHashMap["maxLength"] = InputFilter.LengthFilter(length)
            textField!!.filters = getFilters()
        }

        return this@HTInputCheckTextFields
    }

    override fun inputType(): BaseInputCheckTextFields {
        if (!initView) {
            throw NullPointerException("initView Failure Error!")
        }

        textField!!.inputType

        return this@HTInputCheckTextFields
    }

    private fun getFilters() = filterHashMap.values.toTypedArray()
}