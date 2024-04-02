import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sampleapp.core.designsystem.component.BaseComposeView
import com.example.sampleapp.core.designsystem.component.BaseLogic
import com.example.sampleapp.core.designsystem.component.DefaultVerifyType
import com.example.sampleapp.core.designsystem.component.Verification
import com.example.sampleapp.core.designsystem.component.VerifyType

/**
 * InputCheckTextFields 관련 Logic Interface
 */
interface HTSearchBarLogic : BaseLogic {
    fun checkMaxLength(text: String): Boolean

    fun executeVerification(text: String)
}

/**
 * 첫 번째 테스트  HTInputCheckTextFieldsView
 */
open class HTSearchBarView(
    protected val layoutModifier: Modifier? = null,
    protected val searchTextField: SearchBarTextField,
    protected val searchBarButton: SearchBarButton
) : BaseComposeView, HTSearchBarLogic {
    private var textMutableStateOf: MutableState<String> = mutableStateOf("")

    /**
     * HTSearchBarView InputText 객체 관련 객체
     *
     * @property maxLength
     * @property inputHint
     * @property inputTextStyle
     * @property hintTextStyle
     * @property shape
     * @property modifier
     * @property textVerification
     */
    @Immutable
    data class SearchBarTextField(
        val maxLength: Int = 0,
        val inputHint: String? = null,
        val inputTextStyle: TextStyle? = null,
        val hintTextStyle: TextStyle? = null,
        val shape: Shape? = null,
        val modifier: Modifier? = null,
        val textVerification: Verification<VerifyType>? = null
    )

    /**
     * HTSearchBarView Button 객체 관련 데이터 객체
     *
     * @property buttonText
     * @property textStyle
     * @property shape
     * @property modifier
     * @property buttonListener
     */
    @Immutable
    data class SearchBarButton(
        val buttonText: String?,
        val textStyle: TextStyle? = null,
        val shape: Shape? = null,
        val modifier: Modifier? = null,
        val buttonListener: SearchButtonListener
    )

    /**
     *
     *
     */
    interface SearchButtonListener {
        fun onClick(inputText: String)
    }

    @Composable
    override fun OnDraw() {
        //View 초기화
        Row(
            modifier = layoutModifier ?: Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textFieldModifier = searchTextField.modifier ?: Modifier
                .weight(0.75f)
                .fillMaxHeight()

            val buttonModifier = searchBarButton.modifier ?: Modifier
                .weight(0.2f)
                .wrapContentHeight()
                .padding(start = 20.dp)

            TextFieldSearchBar(textFieldModifier, searchTextField)
            ButtonSearchBar(buttonModifier, searchBarButton)
        }
    }

    @Composable
    protected fun TextFieldSearchBar(modifier: Modifier, searchTextField: SearchBarTextField) {
        var textRemember by remember { textMutableStateOf }

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
            placeholder = {
                val hint = searchTextField.inputHint ?: ""
                Text(
                    text = hint,
                    style = searchTextField.hintTextStyle ?: LocalTextStyle.current.copy()
                )
            },
            singleLine = true,
            modifier = modifier,
            textStyle = searchTextField.inputTextStyle ?: LocalTextStyle.current.copy(),
            shape = searchTextField.shape ?: RoundedCornerShape(9999.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }

    @Composable
    protected fun ButtonSearchBar(modifier: Modifier, searchBarButton: SearchBarButton) {
        val textRemember by remember { textMutableStateOf }

        TextButton(
            onClick = { searchBarButton.buttonListener.onClick(textRemember) },
            shape = searchBarButton.shape ?: ButtonDefaults.textShape,
            modifier = modifier
        ) {
            Text(
                text = searchBarButton.buttonText ?: "",
                style = searchBarButton.textStyle
                    ?: LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )
        }
    }

    override fun checkMaxLength(text: String): Boolean {
        return text.length <= searchTextField.maxLength
    }

    @Throws(NullPointerException::class)
    override fun executeVerification(text: String) {
        //검증 기능
        val result = searchTextField.textVerification?.verify(text)
            ?: throw BaseComposeView.ComposeViewError(
                "Verification",
                Throwable("HTInputCheckTextFields verification is null")
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
}