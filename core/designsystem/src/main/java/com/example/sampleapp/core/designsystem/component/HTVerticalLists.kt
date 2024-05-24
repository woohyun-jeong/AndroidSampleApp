package com.example.sampleapp.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sampleapp.core.designsystem.base.BaseComposeView
import com.example.sampleapp.core.designsystem.base.BaseStyle
import com.example.sampleapp.core.designsystem.base.BaseViewData
import com.example.sampleapp.core.designsystem.base.VerifyType
import com.example.sampleapp.core.designsystem.data.EmptyItemData

interface ListType

open class HTVerticalListEmptyItemStyle(
    val titleTextStyle: TextStyle? = null,
    val subTitleTextStyle: TextStyle? = null
) : BaseStyle

/**
 * item Shape Type
 */
sealed interface ListShapeType : ListType {
    object EmptyItemType : ListShapeType
    object CheckBoxItemType : ListShapeType
    object ExpandableItemType : ListShapeType
    object StickyHeaderItemType : ListShapeType
}

/**
 * VerticalList Verify Type
 */
sealed class DefaultVerifyTypeVerticalList : VerifyType {
    object VerifyNoDataError : DefaultVerifyTypeVerticalList()
}

@Composable
fun EmptyItem(data: BaseViewData, out : () -> Unit){
    val item = data as EmptyItemData
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                out()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        object : EmptyItemTitle(item.title) {
            override fun defineStyleType(): HTVerticalListEmptyItemStyle {
                return super.defineStyleType()
            }
        }.OnDraw()
        object : EmptyItemSubTitle(item.contents) {
            override fun defineStyleType(): HTVerticalListEmptyItemStyle {
                return super.defineStyleType()
            }
        }.OnDraw()

    }

}

open class EmptyItemTitle(title: String) : BaseComposeView, BaseComposeView.ComposeViewStyle<BaseStyle> {
    val title = title
    val style = defineStyleType()
    @Composable
    override fun OnDraw() {
        Text(
            text = title,
            color = style.titleTextStyle?.color ?: Color.Black,
            fontSize = style.titleTextStyle?.fontSize ?: 50.sp,
            fontWeight = style.titleTextStyle?.fontWeight ?: FontWeight.Bold
        )
    }

    override fun defineStyleType(): HTVerticalListEmptyItemStyle {
        return HTVerticalListEmptyItemStyle()
    }

}

open class EmptyItemSubTitle(subTitle: String) : BaseComposeView, BaseComposeView.ComposeViewStyle<BaseStyle> {
    val subTitle = subTitle
    val style = defineStyleType()
    @Composable
    override fun OnDraw() {
        Text(
            text = subTitle,
            color = style.subTitleTextStyle?.color ?: Color.Black,
            fontSize = style.subTitleTextStyle?.fontSize ?: 30.sp,
            fontWeight = style.subTitleTextStyle?.fontWeight ?: FontWeight.Normal
        )
    }

    override fun defineStyleType(): HTVerticalListEmptyItemStyle {
        return HTVerticalListEmptyItemStyle()
    }

}

open class HTVerticalLists(private val items: List<BaseViewData>? = null,
                           private val verification: Verification<VerifyType>? = null,
                           private val out : (page: Int) -> Unit): BaseComposeView {
    interface Verification<type : VerifyType> {
        fun verify(input: String): type

        class VerificationTypeError(msg: String) : Throwable(msg)
    }
    @Composable
    override fun OnDraw() {
        LazyColumn(contentPadding =  PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)){
            if (items != null) {
                items(items.size) {
                    index ->
                    EmptyItem(data = items[index]) {
                        out(index)
                    }

                }
            } else {
                executeVerification("list data null")
            }

        }

    }

    @Throws(NullPointerException::class)
    fun executeVerification(text: String) {
        //검증 기능
    }

}