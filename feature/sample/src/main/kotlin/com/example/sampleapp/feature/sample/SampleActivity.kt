package com.example.sampleapp.feature.sample

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapp.core.data.api.websocket.WebSocketListener
import com.example.sampleapp.core.data.api.websocket.WebSocketTestClient
import com.example.sampleapp.core.data.event.EventObserver
import com.example.sampleapp.core.designsystem.theme.Blue01
import com.example.sampleapp.core.designsystem.theme.Gray
import com.example.sampleapp.core.designsystem.theme.KnightsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.TestOnly
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

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

        CoroutineScope(Dispatchers.Default).launch {
            EventObserver.eventSubscriber.collect {
                Log.d("SampleActivity", "eventSubscriber id = ${it.id}, data = ${it.data.getString("message")}")

            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
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
        Spacer(Modifier.requiredHeight(spaceHeight))
        PrimaryButton()
        CloseButton()
        Spacer(Modifier.requiredHeight(spaceHeight))
        Spacer(Modifier.requiredHeight(spaceHeight))
        Spacer(Modifier.requiredHeight(spaceHeight))
    }
}

@Composable
private fun PrimaryButton(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Button(
        shape = RectangleShape,
        onClick = {
            Toast.makeText(context, "클릭버튼", Toast.LENGTH_SHORT).show()
            if (WebSocketTestClient.webSocket == null) {
                WebSocketTestClient.webSocket = WebSocketTestClient.client.newWebSocket(
                    WebSocketTestClient.request,
                    WebSocketTestClient.listener
                )
            }

        },
        colors = ButtonDefaults.buttonColors(
            contentColor = Blue01,
            containerColor = Blue01,
            disabledContainerColor = Gray,
            disabledContentColor = Gray
        ),
        modifier = modifier
    ) {
        Text(text = "테스트", color = Color.White)
    }
}

@Composable
private fun CloseButton(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Button(
        shape = RectangleShape,
        onClick = {
            Toast.makeText(context, "종료버튼", Toast.LENGTH_SHORT).show()
            if (WebSocketTestClient.webSocket != null) {
                WebSocketTestClient.webSocket?.close(WebSocketListener.NORMAL_CLOSURE_STATUS, null)
                WebSocketTestClient.webSocket = null
            }

        },
        colors = ButtonDefaults.buttonColors(
            contentColor = Blue01,
            containerColor = Blue01,
            disabledContainerColor = Gray,
            disabledContentColor = Gray
        ),
        modifier = modifier
    ) {
        Text(text = "종료", color = Color.White)
    }
}

@TestOnly
@Preview(showBackground = true, widthDp = 500, heightDp = 600)
@Composable
private fun TestBoxWithConstraints() {
    BoxWithConstraints {
        val rectangleHeight = 100.dp
        if (maxHeight < rectangleHeight * 2) {
            Box(
                Modifier
                    .size(50.dp, rectangleHeight)
                    .background(Color.Blue)
            )
        } else {
            Column {
                Box(
                    Modifier
                        .size(50.dp, rectangleHeight)
                        .background(Color.Blue)
                )
                Box(
                    Modifier
                        .size(50.dp, rectangleHeight)
                        .background(Color.Gray)
                )
            }
        }
    }
}

@TestOnly
@Preview(showBackground = true, widthDp = 500, heightDp = 600)
@Composable
private fun TestPrimaryButton() {
    PrimaryButton()
}
