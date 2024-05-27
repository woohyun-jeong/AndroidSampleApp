package com.example.sampleapp.core.ui

import HTIndicatorStyle
import HTIndicatorView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


private val style = HTIndicatorStyle(
    indicatorModifier = Modifier.size(10.dp),
    indicatorShapeColor = Color.Cyan,
    indicatorShape = RectangleShape,
    indicatorIntervalPadding = 10.dp,
    indicatorShapeActiveColor = Color.Green,
    indicatorHorizontalArrangement = Arrangement.Center,
    indicatorVerticalArrangement = Alignment.CenterVertically
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
        .fillMaxWidth().height(100.dp).background(Color.Magenta)

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