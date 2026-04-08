package com.chelo.splitmouse.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.chelo.splitmouse.R

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


val BeVietnamPro = FontFamily(
    Font(R.font.be_vietnam_pro_thin, FontWeight.Thin),
    Font(R.font.be_vietnam_pro_thinitalic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.be_vietnam_pro_extralight, FontWeight.ExtraLight),
    Font(R.font.be_vietnam_pro_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.be_vietnam_pro_regular, FontWeight.Normal),
    Font(R.font.be_vietnam_pro_medium, FontWeight.Medium),
    Font(R.font.be_vietnam_pro_semibold, FontWeight.SemiBold),
    Font(R.font.be_vietnam_pro_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.be_vietnam_pro_bold, FontWeight.Bold),
    Font(R.font.be_vietnam_pro_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.be_vietnam_pro_extrabold, FontWeight.ExtraBold),
    Font(R.font.be_vietnam_pro_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.be_vietnam_pro_black, FontWeight.Black),
    Font(R.font.be_vietnam_pro_blackitalic, FontWeight.Black, FontStyle.Italic)
)

// Configuramos la tipografía base de Material 3 para que use esta familia
val AppTypography = androidx.compose.material3.Typography(
    displayLarge = TextStyle(fontFamily = BeVietnamPro),
    displayMedium = TextStyle(fontFamily = BeVietnamPro),
    displaySmall = TextStyle(fontFamily = BeVietnamPro),
    headlineLarge = TextStyle(fontFamily = BeVietnamPro),
    headlineMedium = TextStyle(fontFamily = BeVietnamPro),
    headlineSmall = TextStyle(fontFamily = BeVietnamPro),
    titleLarge = TextStyle(fontFamily = BeVietnamPro),
    titleMedium = TextStyle(fontFamily = BeVietnamPro),
    titleSmall = TextStyle(fontFamily = BeVietnamPro),
    bodyLarge = TextStyle(fontFamily = BeVietnamPro),
    bodyMedium = TextStyle(fontFamily = BeVietnamPro),
    bodySmall = TextStyle(fontFamily = BeVietnamPro),
    labelLarge = TextStyle(fontFamily = BeVietnamPro),
    labelMedium = TextStyle(fontFamily = BeVietnamPro),
    labelSmall = TextStyle(fontFamily = BeVietnamPro)
)
@Composable
fun SplitMouseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}