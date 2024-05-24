package com.example.sampleapp.core.ui

import HTIndicatorStyle
import HTIndicatorView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


private val style = HTIndicatorStyle(
    /*badgeModifier = Modifier.fillMaxSize(),
    badgeTextStyle = Typography.bodyMediumR.copy(),
    badgeTextSize = 8.sp,
    iconModifier = Modifier
        .fillMaxSize()
        .background(Color.Gray)
        .padding(2.dp),*/
)

//예제 테스트 용 코드
val currentPositionMutableState = mutableStateOf(0)

@Preview(widthDp = 200, heightDp = 200)
@Composable
fun HTIndicatorBadge() {
    val rememberCurrentPosition = remember {
        currentPositionMutableState
    }

    val defaultLayoutModifier = Modifier
        .fillMaxSize()

    val defaultIndicator = HTIndicatorView.Indicator(
        indicatorCount = 10,
        indicatorCurrentPosition = rememberCurrentPosition.value,
        indicatorClickEvent = { position ->
            rememberCurrentPosition.value = position
        }
    )

    val view = object : HTIndicatorView(
        layoutModifier = defaultLayoutModifier,
        indicator = defaultIndicator
    ) {
        override fun defineStyleType(): HTIndicatorStyle {
            return style
        }
    }

    view.OnDraw()
}