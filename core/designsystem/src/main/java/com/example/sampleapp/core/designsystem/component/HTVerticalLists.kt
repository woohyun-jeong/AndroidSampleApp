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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sampleapp.core.designsystem.base.BaseComposeView
import com.example.sampleapp.core.designsystem.base.BaseStyle
import com.example.sampleapp.core.designsystem.base.VerifyType

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
interface DefaultData
data class EmptyItemData(
    val id: Int,
    val title: String,
    val contents: String
): DefaultData

class TestSampleRepository {
    fun getAllData(): List<EmptyItemData> {
        return listOf(
            EmptyItemData(0, "test11","hi test!!!"),
            EmptyItemData(1,"test2","hello"),
            EmptyItemData(2,"test3","good"),
            EmptyItemData(3,"test4","best"),
            EmptyItemData(4,"test5","Test is LazyColumn Item"),
            EmptyItemData(5,"test6","hello world")
        )
    }
}

@Composable
fun EmptyItem(data: DefaultData, out : () -> Unit){
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
            color = style.titleTextStyle?.color ?: Color.Black,
            fontSize = style.titleTextStyle?.fontSize ?: 30.sp,
            fontWeight = style.titleTextStyle?.fontWeight ?: FontWeight.Normal
        )
    }

    override fun defineStyleType(): HTVerticalListEmptyItemStyle {
        return HTVerticalListEmptyItemStyle()
    }

}

open class HTVerticalLists(private val items: List<DefaultData>? = null,
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
//        val result = verification?.verify(text) ?: throw BaseComposeView.ComposeViewError(
//            "Verification",
//            Throwable("HTInputCheckTextFields verification is null")
//        )

//        when (result) {
//            is DefaultVerifyType.VerifyMaxInputTextError -> {
//            }
//
//            is DefaultVerifyType.VerifyAlreadyExistTextError -> {
//            }
//
//            is DefaultVerifyType.VerifyTextVerifyError -> {
//            }
//
//            is DefaultVerifyType.VerifyOk -> {
//            }
//
//            else -> throw HTInputCheckTextFieldsView.Verification.VerificationTypeError("HTInputCheckTextFields verify() Result Type is else!")
//        }
    }

}