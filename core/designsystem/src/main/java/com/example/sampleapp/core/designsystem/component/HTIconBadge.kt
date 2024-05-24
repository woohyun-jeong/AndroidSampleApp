import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sampleapp.core.designsystem.base.BaseComposeView
import com.example.sampleapp.core.designsystem.base.BaseLogic
import com.example.sampleapp.core.designsystem.base.BaseStyle
import com.example.sampleapp.core.designsystem.base.BaseViewData
import com.example.sampleapp.core.designsystem.data.Image

/**
 * IconBadge 관련 Logic Interface
 */
interface HTIconBadgeLogic : BaseLogic {
    fun defineBadgeCount(count: Int): String
}

/**
 * 기본 HTIconBadgeStyle
 *
 */
open class HTIconBadgeStyle(
    var badgeModifier: Modifier? = null,
    val badgeTextStyle: TextStyle? = null,
    val badgeTextColor: Color? = null,
    val badgeTextSize: TextUnit? = null,
    val badgeContentAlignment: Alignment? = null,
    val badgeShape: Shape? = null,
    val badgeShapeColor: Color? = null,
    var iconModifier: Modifier? = null
) : BaseStyle


/**
 * HTIconBadgeView
 *
 * @property layoutModifier
 * @property badge
 */
open class HTIconBadgeView(
    protected val layoutModifier: Modifier? = null,
    protected val badge: BaseViewData
) : BaseComposeView, BaseComposeView.ComposeViewStyle<BaseStyle> {

    /**
     * Badge
     *
     * @property count
     * @property iconImage
     * @property iconBadgeLogic
     */
    @Immutable
    data class Badge(
        val count: Int = 0,
        val iconImage: Image? = null,
        val iconBadgeLogic: HTIconBadgeLogic? = null
    ) : BaseViewData

    @Composable
    override fun OnDraw() {
        val badge = kotlin.runCatching {
            badge as Badge
        }.getOrNull() ?: throw BaseComposeView.ComposeViewError(
            this@HTIconBadgeView.javaClass.simpleName,
            Error("searchBarButton type not SearchBarButton")
        )

        val defaultLayoutModifier = Modifier
            .size(90.dp)
            .padding(20.dp)
            .background(Color.Black)

        //View 초기화
        BoxWithConstraints(
            modifier = layoutModifier ?: defaultLayoutModifier,
            contentAlignment = Alignment.Center
        ) {
            val targetStyle = defineStyleType()

            //BadgeIconView UI 추가
            object : BadgeIconView(
                badge.iconImage
            ) {
                override fun defineStyleType(): HTIconBadgeStyle {
                    return targetStyle
                }
            }.also { view ->
                view.OnDraw()
            }

            //BadgeView UI 추가
            object : BadgeView(
                badgeCount = badge.count,
                logic = badge.iconBadgeLogic
            ) {
                override fun defineStyleType(): HTIconBadgeStyle {
                    return targetStyle
                }
            }.also { view ->
                view.OnDraw()
            }
        }
    }

    /**
     * BadgeIconView
     *
     * @property badgeIconImage
     */
    open class BadgeIconView(
        private val badgeIconImage: Image? = null
    ) : BaseComposeView, BaseComposeView.ComposeViewStyle<BaseStyle> {

        @Composable
        override fun OnDraw() {
            val targetStyle = defineStyleType()
            val defaultIconModifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)

            BoxWithConstraints(
                modifier = targetStyle.iconModifier ?: defaultIconModifier,
                contentAlignment = Alignment.Center
            ) {
                if (badgeIconImage != null) {
                    Image(
                        painterResource(badgeIconImage.id),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(badgeIconImage.size),
                        contentScale = badgeIconImage.contentScale
                    )
                } else {
                    throw BaseComposeView.ComposeViewError(
                        "HTIconBadgeView", NullPointerException()
                    )
                }
            }
        }

        override fun defineStyleType(): HTIconBadgeStyle {
            return HTIconBadgeStyle()
        }
    }

    /**
     * BadgeView
     *
     * @property badgeCount
     * @property logic
     */
    open class BadgeView(
        private var badgeCount: Int? = null,
        private var logic: HTIconBadgeLogic? = null
    ) : BaseComposeView, HTIconBadgeLogic, BaseComposeView.ComposeViewStyle<BaseStyle> {

        @Composable
        override fun OnDraw() {
            val targetStyle = defineStyleType()
            val defaultBadgeModifier = Modifier.fillMaxSize()

            BoxWithConstraints(
                modifier = targetStyle.badgeModifier ?: defaultBadgeModifier,
                contentAlignment = targetStyle.badgeContentAlignment ?: Alignment.TopEnd
            ) {
                val viewCount = defineBadgeCount(badgeCount ?: 0)
                val badgeShape = targetStyle.badgeShape ?: RoundedCornerShape(9999.dp)
                val badgeShapeColor = targetStyle.badgeShapeColor ?: Color.Red
                val badgeTextColor = targetStyle.badgeTextColor ?: Color.White
                val badgeTextSize = targetStyle.badgeTextSize ?: 10.sp
                val badgeTextStyle = targetStyle.badgeTextStyle ?: LocalTextStyle.current

                val textModifier = Modifier
                    .wrapContentSize()
                    .padding(2.dp)
                    .background(badgeShapeColor, badgeShape)

                Text(
                    modifier = textModifier,
                    text = "  $viewCount  ",
                    color = badgeTextColor,
                    fontSize = badgeTextSize,
                    style = badgeTextStyle,
                    textAlign = TextAlign.Center
                )
            }
        }

        override fun defineBadgeCount(count: Int): String {
            return logic?.defineBadgeCount(count) ?: count.toString()
        }

        override fun defineStyleType(): HTIconBadgeStyle {
            return HTIconBadgeStyle()
        }
    }

    override fun defineStyleType(): HTIconBadgeStyle {
        return HTIconBadgeStyle()
    }
}