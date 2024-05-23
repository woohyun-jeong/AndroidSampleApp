package com.example.sampleapp.core.ui

import HTIconBadgeLogic
import HTIconBadgeStyle
import HTIconBadgeView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sampleapp.core.designsystem.data.Image
import com.example.sampleapp.core.designsystem.theme.Typography


private val style = HTIconBadgeStyle(
    badgeModifier = Modifier.fillMaxSize(),
    badgeTextStyle = Typography.bodyMediumR.copy(),
    badgeTextSize = 8.sp,
    iconModifier = Modifier
        .fillMaxSize()
        .background(Color.Gray)
        .padding(2.dp),
)

//예제 테스트 용 코드
val countMutableState = mutableStateOf(0)

@Preview(widthDp = 200, heightDp = 200)
@Composable
fun HTIconBadge() {
    val countRemember by countMutableState

    val defaultLayoutModifier = Modifier
        .size(90.dp)
        .padding(20.dp)

    val view = object : HTIconBadgeView(
        defaultLayoutModifier,
        Badge(
            count = countRemember,
            iconImage = Image(
                R.drawable.img_cat_archer,
                50.dp,
                ContentScale.Crop
            ),
            iconBadgeLogic = object : HTIconBadgeLogic {
                override fun defineBadgeCount(count: Int): String {
                    return if (count > 100) {
                        "100+"
                    } else {
                        count.toString()
                    }
                }
            })
    ) {
        override fun defineStyleType(): HTIconBadgeStyle {
            return style
        }
    }

    view.OnDraw()
}


@Preview(widthDp = 90, heightDp = 90)
@Composable
fun HTIconBadge2() {
    val countRemember by countMutableState

    val style = HTIconBadgeStyle(
        badgeModifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        badgeShape = RectangleShape,
        badgeShapeColor = Color.Blue,
        badgeTextStyle = Typography.bodyMediumR.copy(),
        badgeTextSize = 9.sp,
        badgeContentAlignment = Alignment.BottomStart,
        iconModifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(2.dp),
    )

    val defaultLayoutModifier = Modifier
        .size(90.dp)
        .padding(20.dp)

    val view = object : HTIconBadgeView(
        defaultLayoutModifier,
        Badge(
            count = countRemember,
            iconImage = Image(
                R.drawable.img_cat_knight,
                30.dp,
                ContentScale.Crop
            )
        )
    ) {
        override fun defineStyleType(): HTIconBadgeStyle {
            return style
        }
    }

    view.OnDraw()
}