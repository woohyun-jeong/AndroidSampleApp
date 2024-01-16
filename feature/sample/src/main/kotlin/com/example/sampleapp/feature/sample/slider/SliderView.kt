package com.example.sampleapp.feature.sample.slider

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@ExperimentalMaterial3Api
@Preview
@Composable
fun SliderSample() {
    var sliderPosition by remember { mutableStateOf(0f) }
    Slider(value = sliderPosition, onValueChange = { sliderPosition = it })
}

@Preview
@Composable
fun StepsSliderSample() {
    var sliderPosition by remember { mutableStateOf(0f) }
    Slider(
        value = sliderPosition,
        onValueChange = { sliderPosition = it },
        valueRange = 0f..100f,
        onValueChangeFinished = {
            // some business logic update with the state you hold
        },
        steps = 5,
        colors = SliderDefaults.colors(
            thumbColor = Color.DarkGray,
            activeTrackColor = Color.Cyan
        )
    )
}