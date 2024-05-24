import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.sampleapp.core.designsystem.base.BaseComposeView
import com.example.sampleapp.core.designsystem.base.BaseLogic
import com.example.sampleapp.core.designsystem.base.BaseStyle
import com.example.sampleapp.core.designsystem.base.BaseViewData
import com.example.sampleapp.core.designsystem.base.DefaultVerifyType
import com.example.sampleapp.core.designsystem.base.Verification
import com.example.sampleapp.core.designsystem.base.VerifyType

/**
 * InputCheckTextFields 관련 Logic Interface
 */
interface HTSearchBarLogic : BaseLogic {
    fun checkMaxLength(text: String): Boolean

    fun executeVerification(text: String)
}

/**
 * 기본 HTSearchBarStyle
 *
 * @property inputModifier
 * @property inputTextStyle
 * @property inputHintTextStyle
 * @property inputShape
 * @property buttonModifier
 * @property buttonTextStyle
 * @property buttonShape
 */
open class HTSearchBarStyle(
    var inputModifier: Modifier? = null,
    val inputTextStyle: TextStyle? = null,
    val inputHintTextStyle: TextStyle? = null,
    val inputShape: Shape? = null,
    var buttonModifier: Modifier? = null,
    val buttonTextStyle: TextStyle? = null,
    val buttonShape: Shape? = null,
) : BaseStyle

/**
 * 기본 HTSearchBarStyle2
 *
 * @property addStyle
 * @constructor
 * HTSearchBarStyle 상속 데이터
 *
 * @param inputModifier
 * @param inputTextStyle
 * @param inputHintTextStyle
 * @param inputShape
 * @param buttonModifier
 * @param buttonTextStyle
 * @param buttonShape
 */
class HTSearchBarStyle2(
    var addStyle: String,
    inputModifier: Modifier? = null,
    inputTextStyle: TextStyle? = null,
    inputHintTextStyle: TextStyle? = null,
    inputShape: Shape? = null,
    buttonModifier: Modifier? = null,
    buttonTextStyle: TextStyle? = null,
    buttonShape: Shape? = null,
) : HTSearchBarStyle(
    inputModifier,
    inputTextStyle,
    inputHintTextStyle,
    inputShape,
    buttonModifier,
    buttonTextStyle,
    buttonShape
)

/**
 * 첫 번째 테스트  HTInputCheckTextFieldsView
 */
open class HTSearchBarView(
    protected val layoutModifier: Modifier? = null,
    protected val searchTextField: BaseViewData,
    protected val searchBarButton: BaseViewData
) : BaseComposeView, BaseComposeView.ComposeViewStyle<BaseStyle> {
    var textMutableStateOf: MutableState<String> = mutableStateOf("")

    /**
     * HTSearchBarView InputText 객체 관련 객체
     *
     * @property maxLength
     * @property inputHint
     * @property textVerification
     */
    @Immutable
    data class SearchBarTextField(
        val maxLength: Int = 0,
        val inputHint: String? = null,
        val textVerification: Verification<VerifyType>? = null
    ) : BaseViewData

    /**
     * HTSearchBarView Button 객체 관련 데이터 객체
     *
     * @property buttonText
     * @property buttonListener
     */
    @Immutable
    data class SearchBarButton(
        val buttonText: String?,
        val buttonListener: SearchButtonListener
    ) : BaseViewData

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
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val defaultInputModifier = Modifier
                .weight(0.75f)
                .fillMaxHeight()

            val defaultButtonModifier = Modifier
                .weight(0.25f)
                .fillMaxHeight()
                .padding(start = 20.dp)

            val targetStyle = defineStyleType()

            when {
                targetStyle.inputModifier == null -> targetStyle.inputModifier =
                    defaultInputModifier

                targetStyle.buttonModifier == null -> targetStyle.buttonModifier =
                    defaultButtonModifier
            }

            //SearchBarTextField 그리기
            when (searchTextField) {
                is SearchBarTextField -> {
                    object : TextFieldSearchBarView(searchTextField, textMutableStateOf) {
                        override fun defineStyleType(): HTSearchBarStyle {
                            return targetStyle
                        }
                    }.OnDraw()
                }

                else -> throw BaseComposeView.ComposeViewError(
                    this@HTSearchBarView.javaClass.simpleName,
                    Error("searchTextField type not SearchBarTextField")
                )
            }

            //SearchBarButton 그리기
            when (searchBarButton) {
                is SearchBarButton -> {
                    object : ButtonSearchBarView(searchBarButton, textMutableStateOf) {
                        override fun defineStyleType(): HTSearchBarStyle {
                            return targetStyle
                        }
                    }.OnDraw()
                }

                else -> throw BaseComposeView.ComposeViewError(
                    this@HTSearchBarView.javaClass.simpleName,
                    Error("searchBarButton type not SearchBarButton")
                )
            }
        }
    }

    /**
     * 기본 TextFieldSearchBar
     *
     * @property searchTextField
     * @property textMutableStateOf
     */
    open class TextFieldSearchBarView(
        private val searchTextField: SearchBarTextField,
        private val textMutableStateOf: MutableState<String>
    ) : BaseComposeView, HTSearchBarLogic, BaseComposeView.ComposeViewStyle<BaseStyle> {

        @Composable
        override fun OnDraw() {
            val style = defineStyleType()
            val modifier = style.inputModifier ?: return
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
                        style = style.inputHintTextStyle ?: LocalTextStyle.current.copy()
                    )
                },
                singleLine = true,
                modifier = modifier,
                textStyle = style.inputTextStyle ?: LocalTextStyle.current.copy(),
                shape = style.buttonShape ?: RoundedCornerShape(9999.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        override fun checkMaxLength(text: String): Boolean {
            return text.length <= searchTextField.maxLength
        }

        @Throws(NullPointerException::class)
        override fun executeVerification(text: String) {
            //검증 기능
            val result = searchTextField.textVerification?.verify(text) ?: return

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

        override fun defineStyleType(): HTSearchBarStyle {
            return HTSearchBarStyle()
        }

    }

    /**
     * 기본 ButtonSearchBar
     *
     * @property searchBarButton
     * @property textMutableStateOf
     */
    open class ButtonSearchBarView(
        private val searchBarButton: SearchBarButton,
        private val textMutableStateOf: MutableState<String>
    ) : BaseComposeView, BaseComposeView.ComposeViewStyle<BaseStyle> {

        @Composable
        override fun OnDraw() {
            val style = defineStyleType()
            val modifier = style.buttonModifier ?: return
            val textRemember by remember { textMutableStateOf }

            TextButton(
                onClick = { searchBarButton.buttonListener.onClick(textRemember) },
                shape = style.buttonShape ?: ButtonDefaults.textShape,
                modifier = modifier
            ) {
                Text(
                    text = searchBarButton.buttonText ?: "",
                    style = style.buttonTextStyle
                        ?: LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                )
            }
        }

        override fun defineStyleType(): HTSearchBarStyle {
            return HTSearchBarStyle()
        }

    }

    override fun defineStyleType(): HTSearchBarStyle {
        return HTSearchBarStyle()
    }

}

/**
 * 두 번째 테스트  HTInputCheckTextFieldsView 상속
 */
class HTSearchBarView2(
    layoutModifier: Modifier? = null,
    searchTextField: SearchBarTextField,
    searchBarButton: SearchBarButton
) : HTSearchBarView(layoutModifier, searchTextField, searchBarButton) {
    @Composable
    override fun OnDraw() {
        //View 초기화
        Row(
            modifier = layoutModifier ?: Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val defaultInputModifier = Modifier
                .weight(0.75f)
                .fillMaxHeight()

            val defaultButtonModifier = Modifier
                .weight(0.25f)
                .fillMaxHeight()
                .padding(start = 20.dp)

            val targetStyle = defineStyleType()

            //TODO 추가된 스타일에 대해서 정의
            targetStyle.addStyle

            when {
                targetStyle.inputModifier == null -> targetStyle.inputModifier =
                    defaultInputModifier

                targetStyle.buttonModifier == null -> targetStyle.buttonModifier =
                    defaultButtonModifier
            }

            //SearchBarTextField 그리기
            when (searchTextField) {
                is SearchBarTextField -> {
                    object : TextFieldSearchBarView(searchTextField, textMutableStateOf) {
                        override fun defineStyleType(): HTSearchBarStyle {
                            return targetStyle
                        }
                    }.OnDraw()
                }

                else -> throw BaseComposeView.ComposeViewError(
                    this@HTSearchBarView2.javaClass.simpleName,
                    Error("searchTextField type not SearchBarTextField")
                )
            }

            //SearchBarButton 그리기
            when (searchBarButton) {
                is SearchBarButton -> {
                    object : ButtonSearchBarView(searchBarButton, textMutableStateOf) {
                        override fun defineStyleType(): HTSearchBarStyle {
                            return targetStyle
                        }
                    }.OnDraw()
                }

                else -> throw BaseComposeView.ComposeViewError(
                    this@HTSearchBarView2.javaClass.simpleName,
                    Error("searchBarButton type not SearchBarButton")
                )
            }
        }
    }

    override fun defineStyleType(): HTSearchBarStyle2 {
        return HTSearchBarStyle2("TEST")
    }
}