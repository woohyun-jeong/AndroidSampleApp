package com.example.sampleapp.feature.sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapp.core.designsystem.theme.Blue01
import com.example.sampleapp.core.designsystem.theme.Gray
import com.example.sampleapp.core.designsystem.theme.KnightsTheme
import com.example.sampleapp.feature.sample.appbar.AppBarSample
import com.example.sampleapp.feature.sample.nav.BottomNavBar
import com.example.sampleapp.feature.sample.slider.SliderSample
import com.example.sampleapp.feature.sample.slider.StepsSliderSample
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.TestOnly

@AndroidEntryPoint
class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            KnightsTheme(true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SampleScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SampleScreen(
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val spaceHeight = 24.dp
    val context = LocalContext.current
    Column(
        modifier = modifier.navigationBarsPadding(),
    ) {
        AppBarSample(title = "테스트") {
            Toast.makeText(context, "클릭", Toast.LENGTH_SHORT).show()
        }
        Spacer(Modifier.requiredHeight(spaceHeight))
        PrimaryButton()
        Spacer(Modifier.requiredHeight(spaceHeight))
        SliderSample()
        Spacer(Modifier.requiredHeight(spaceHeight))
        StepsSliderSample()
        Spacer(Modifier.requiredHeight(spaceHeight))
        BottomNavBar()
    }
}

@Composable
private fun PrimaryButton(
    modifier: Modifier = Modifier
){
    Button(
        shape = RectangleShape,
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            contentColor = Blue01,
            containerColor = Blue01,
            disabledContainerColor = Gray,
            disabledContentColor =Gray),
        modifier = modifier
    ) {
        Text(text = "테스트", color = Color.White)
    }
}

@TestOnly
@Preview(showBackground = true, widthDp = 500, heightDp = 600)
@Composable
private fun TestBoxWithConstraints(){
    BoxWithConstraints {
        val rectangleHeight = 100.dp
        if (maxHeight < rectangleHeight * 2) {
            Box(
                Modifier
                    .size(50.dp, rectangleHeight)
                    .background(Color.Blue))
        } else {
            Column {
                Box(
                    Modifier
                        .size(50.dp, rectangleHeight)
                        .background(Color.Blue))
                Box(
                    Modifier
                        .size(50.dp, rectangleHeight)
                        .background(Color.Gray))
            }
        }
    }
}

@TestOnly
@Preview(showBackground = true, widthDp = 500, heightDp = 600)
@Composable
private fun TestPrimaryButton(){
    PrimaryButton()
}
