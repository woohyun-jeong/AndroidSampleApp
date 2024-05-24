import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.sampleapp.core.designsystem.base.BaseComposeView
import com.example.sampleapp.core.designsystem.base.BaseStyle
import com.example.sampleapp.core.designsystem.base.BaseViewData


/**
 */
open class HTIndicatorStyle(
    var indicatorModifier: Modifier? = null,
    val indicatorIntervalPadding: Dp? = null,
    val indicatorShape: Shape? = null,
    val indicatorShapeColor: Color? = null,
    val indicatorShapeActiveColor: Color? = null,
    val indicatorBackgroundColor: Color? = null
) : BaseStyle


/**
 */
open class HTIndicatorView(
    protected val layoutModifier: Modifier? = null, protected val indicator: BaseViewData
) : BaseComposeView, BaseComposeView.ComposeViewStyle<BaseStyle> {

    /**
     */
    @Immutable
    data class Indicator(
        val indicatorCount: Int = 0,
        val indicatorCurrentPosition: Int = 0,
        val indicatorClickEvent: (Int) -> Unit
    ) : BaseViewData

    @Composable
    override fun OnDraw() {
        val indicator = kotlin.runCatching {
            indicator as Indicator
        }.getOrNull() ?: throw BaseComposeView.ComposeViewError(
            this@HTIndicatorView.javaClass.simpleName,
            Error("searchBarButton type not SearchBarButton")
        )

        val targetStyle = defineStyleType()
        val defaultLayoutModifier = layoutModifier ?: Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(targetStyle.indicatorBackgroundColor ?: Color.LightGray)

        //View 초기화
        LazyRow(
            modifier = defaultLayoutModifier
        ) {
            items(count = indicator.indicatorCount) { indicatorPosition ->
                //BadgeIconView UI 추가
                val indicatorIntervalPadding = targetStyle.indicatorIntervalPadding ?: 2.dp

                Spacer(modifier = Modifier.width(indicatorIntervalPadding))
                object : IndicatorItemView(
                    modifier = targetStyle.indicatorModifier,
                    position = indicatorPosition,
                    isActive = indicatorPosition == indicator.indicatorCurrentPosition,
                    indicatorClickEvent = indicator.indicatorClickEvent
                ) {
                    override fun defineStyleType(): HTIndicatorStyle {
                        return targetStyle
                    }
                }.also { view ->
                    view.OnDraw()
                }
            }
        }
    }

    open class IndicatorItemView(
        protected val modifier: Modifier? = null,
        protected val position: Int = 0,
        protected val isActive: Boolean = false,
        protected val indicatorClickEvent: (Int) -> Unit
    ) : BaseComposeView, BaseComposeView.ComposeViewStyle<BaseStyle> {

        @Composable
        override fun OnDraw() {
            val targetStyle = defineStyleType()
            val indicatorShape = targetStyle.indicatorShape ?: RoundedCornerShape(999.dp)
            val indicatorShapeColor = if (isActive) {
                targetStyle.indicatorShapeActiveColor ?: Color.DarkGray
            } else {
                targetStyle.indicatorShapeColor ?: Color.Gray
            }
            val modifier = modifier ?: Modifier.size(8.dp)

            Button(modifier = modifier,
                shape = indicatorShape,
                colors = ButtonDefaults.buttonColors(indicatorShapeColor),
                onClick = {
                    indicatorClickEvent.invoke(position)
                }) {

            }
        }

        override fun defineStyleType(): HTIndicatorStyle {
            return HTIndicatorStyle()
        }
    }

    override fun defineStyleType(): HTIndicatorStyle {
        return HTIndicatorStyle()
    }
}