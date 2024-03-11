package com.example.sampleapp.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.sampleapp.core.designsystem.R

private val SansSerifStyle = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal,
)

private val RobotoFamily = FontFamily(
    Font(R.font.roboto_black, FontWeight.Black),
    Font(R.font.roboto_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_thin, FontWeight.Thin),
    Font(R.font.roboto_thin_italic, FontWeight.Thin, FontStyle.Italic),
)
private val RobotoStyle = TextStyle(
    fontFamily = RobotoFamily,
    fontWeight = FontWeight.Normal,
)

private val MontserratFamily = FontFamily(
    Font(R.font.montserrat_black, FontWeight.Black),
    Font(R.font.montserrat_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.montserrat_extra_bold, FontWeight.ExtraBold),
    Font(R.font.montserrat_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.montserrat_extra_light, FontWeight.ExtraLight),
    Font(R.font.montserrat_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.montserrat_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.montserrat_semi_bold, FontWeight.SemiBold),
    Font(R.font.montserrat_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.montserrat_thin, FontWeight.Thin),
    Font(R.font.montserrat_thin_italic, FontWeight.Thin, FontStyle.Italic),
)

private val Montserrat = TextStyle(
    fontFamily = MontserratFamily,
    fontWeight = FontWeight.Normal,
)

internal val Typography = KnightsTypography(
    displayLargeR = Montserrat.copy(
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMediumR = Montserrat.copy(
        fontSize = 45.sp,
        lineHeight = 52.sp,
    ),
    displaySmallR = Montserrat.copy(
        fontSize = 36.sp,
        lineHeight = 44.sp,
    ),
    headlineLargeEB = Montserrat.copy(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.ExtraBold,
    ),
    headlineLargeSB = Montserrat.copy(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    headlineLargeR = Montserrat.copy(
        fontSize = 32.sp,
        lineHeight = 40.sp,
    ),
    headlineMediumB = Montserrat.copy(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Bold,
    ),
    headlineMediumM = Montserrat.copy(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Medium,
    ),
    headlineMediumR = Montserrat.copy(
        fontSize = 28.sp,
        lineHeight = 36.sp,
    ),
    headlineSmallBL = Montserrat.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = (-0.2).sp,
    ),
    headlineSmallM = Montserrat.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Medium,
    ),
    headlineSmallR = Montserrat.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),
    titleLargeBL = Montserrat.copy(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Black,
    ),
    titleLargeB = Montserrat.copy(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Bold,
    ),
    titleLargeM = Montserrat.copy(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Medium,
    ),
    titleLargeR = Montserrat.copy(
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMediumBL = Montserrat.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Black,
    ),
    titleMediumB = Montserrat.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold,
    ),
    titleMediumR = Montserrat.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    titleSmallB = Montserrat.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.25.sp,
    ),
    titleSmallM = Montserrat.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.25.sp,
    ),
    titleSmallM140 = Montserrat.copy(
        fontSize = 14.sp,
        lineHeight = (19.6).sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (-0.2).sp,
    ),
    titleSmallR140 = Montserrat.copy(
        fontSize = 14.sp,
        lineHeight = (19.6).sp,
        letterSpacing = (-0.2).sp,
    ),
    titleSmallR = Montserrat.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelLargeM = Montserrat.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Medium,
    ),
    labelMediumR = Montserrat.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    labelSmallM = Montserrat.copy(
        fontSize = 11.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (-0.2).sp,
    ),
    bodyLargeR = Montserrat.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMediumR = Montserrat.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmallR = Montserrat.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
)

@Immutable
data class KnightsTypography(
    val displayLargeR: TextStyle,
    val displayMediumR: TextStyle,
    val displaySmallR: TextStyle,

    val headlineLargeEB: TextStyle,
    val headlineLargeSB: TextStyle,
    val headlineLargeR: TextStyle,
    val headlineMediumB: TextStyle,
    val headlineMediumM: TextStyle,
    val headlineMediumR: TextStyle,
    val headlineSmallBL: TextStyle,
    val headlineSmallM: TextStyle,
    val headlineSmallR: TextStyle,

    val titleLargeBL: TextStyle,
    val titleLargeB: TextStyle,
    val titleLargeM: TextStyle,
    val titleLargeR: TextStyle,
    val titleMediumBL: TextStyle,
    val titleMediumB: TextStyle,
    val titleMediumR: TextStyle,
    val titleSmallB: TextStyle,
    val titleSmallM: TextStyle,
    val titleSmallM140: TextStyle,
    val titleSmallR: TextStyle,
    val titleSmallR140: TextStyle,

    val labelLargeM: TextStyle,
    val labelMediumR: TextStyle,
    val labelSmallM: TextStyle,

    val bodyLargeR: TextStyle,
    val bodyMediumR: TextStyle,
    val bodySmallR: TextStyle,
)

@Immutable
data class TestTypography(
    val displayLargeR: TextStyle,
    val displayMediumR: TextStyle,
    val displaySmallR: TextStyle,

    val headlineLargeEB: TextStyle,
    val headlineLargeSB: TextStyle,
    val headlineLargeR: TextStyle,
    val headlineMediumB: TextStyle,
    val headlineMediumM: TextStyle,
    val headlineMediumR: TextStyle,
    val headlineSmallBL: TextStyle,
    val headlineSmallM: TextStyle,
    val headlineSmallR: TextStyle,

    val titleLargeBL: TextStyle,
    val titleLargeB: TextStyle,
    val titleLargeM: TextStyle,
    val titleLargeR: TextStyle,
    val titleMediumBL: TextStyle,
    val titleMediumB: TextStyle,
    val titleMediumR: TextStyle,
    val titleSmallB: TextStyle,
    val titleSmallM: TextStyle,
    val titleSmallM140: TextStyle,
    val titleSmallR: TextStyle,
    val titleSmallR140: TextStyle,

    val labelLargeM: TextStyle,
    val labelMediumR: TextStyle,
    val labelSmallM: TextStyle,

    val bodyLargeR: TextStyle,
    val bodyMediumR: TextStyle,
    val bodySmallR: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    KnightsTypography(
        labelSmallM = SansSerifStyle,
        displayLargeR = SansSerifStyle,
        displayMediumR = SansSerifStyle,
        displaySmallR = SansSerifStyle,
        headlineLargeEB = SansSerifStyle,
        headlineLargeSB = SansSerifStyle,
        headlineLargeR = SansSerifStyle,
        headlineMediumB = SansSerifStyle,
        headlineMediumM = SansSerifStyle,
        headlineMediumR = SansSerifStyle,
        headlineSmallBL = SansSerifStyle,
        headlineSmallM = SansSerifStyle,
        headlineSmallR = SansSerifStyle,
        titleLargeBL = SansSerifStyle,
        titleLargeB = SansSerifStyle,
        titleLargeM = SansSerifStyle,
        titleLargeR = SansSerifStyle,
        titleMediumBL = SansSerifStyle,
        titleMediumB = SansSerifStyle,
        titleMediumR = SansSerifStyle,
        titleSmallB = SansSerifStyle,
        titleSmallM = SansSerifStyle,
        titleSmallM140 = SansSerifStyle,
        titleSmallR = SansSerifStyle,
        titleSmallR140 = SansSerifStyle,
        labelLargeM = SansSerifStyle,
        labelMediumR = SansSerifStyle,
        bodyLargeR = SansSerifStyle,
        bodyMediumR = SansSerifStyle,
        bodySmallR = SansSerifStyle,
    )
}
