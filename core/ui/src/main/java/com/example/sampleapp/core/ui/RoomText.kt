package com.example.sampleapp.core.ui

import android.util.Log
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sampleapp.core.designsystem.component.BaseInputCheckTextFields
import com.example.sampleapp.core.designsystem.component.HTInputCheckTextFieldsViewTest
import com.example.sampleapp.core.designsystem.theme.KnightsTheme
import com.example.sampleapp.core.designsystem.theme.LocalTypography
import com.example.sampleapp.core.designsystem.theme.Montserrat
import com.example.sampleapp.core.model.Room

val Room.textRes: Int
    get() = when (this) {
        Room.ETC -> R.string.session_room_keynote
        Room.TRACK1 -> R.string.session_room_track_01
        Room.TRACK2 -> R.string.session_room_track_02
        Room.TRACK3 -> R.string.session_room_track_03
    }

@Composable
fun RoomText(
    room: Room,
    style: TextStyle,
    color: Color = LocalContentColor.current,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    Text(
        text = stringResource(id = room.textRes),
        style = style,
        color = color,
        onTextLayout = onTextLayout,
    )
}


/**
 * Composable
 * 단점
 * - Composable이 Common Module로 빠져서 기능을 추가하려면 Common Module 쪽에서 코드 추가 필요
 * - fun 형태라서 상속도 불가능함
 * 장점
 *
 * - Compose라서 style 같은 경우 LocalContentColor provides contentColor 이와 같이 스타일 바뀌는 느낌으로 가능
 *
 */
@Preview
@Composable
fun HTInputCheckTextFields() {
    KnightsTheme {
        val image = HTInputCheckTextFieldsViewTest.Image(
            id = R.drawable.ic_contributor_placeholder_darkmode,
            size = 45.dp,
            contentScale = ContentScale.Fit
        )

        val textStyle = LocalTypography.current.headlineLargeSB.copy(textAlign = TextAlign.End)

        val view =
            object : HTInputCheckTextFieldsViewTest<BaseInputCheckTextFields.DefaultVerifyType>(
                image = image,
                textStyle = textStyle,
                maxLength = 20
            ) {
                override fun verify(input: String): BaseInputCheckTextFields.DefaultVerifyType {
                    return BaseInputCheckTextFields.DefaultVerifyType.Ok
                }
            }

        view.OnDraw()
    }

}