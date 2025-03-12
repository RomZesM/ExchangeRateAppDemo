package com.example.exchangerateapptest.ui.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


data class ExtendedColorScheme(
	val primary: Color = Primary,
	val lightPrimary: Color = LightPrimary,
	val onPrimary: Color = OnPrimary,
	val secondary: Color = Secondary,
	val textDefault: Color = TextDefault,
	val textSecondary: Color = TextSecondary,
	val outline: Color = Outline,
	val yellow: Color = Yellow,
	val bgDefault: Color = BgDefault,
	val bgHeader: Color = BgHeader,
	val bgCard: Color = BgCard,
)

private val DarkColorScheme = darkColorScheme(
	primary = Purple80,
	secondary = PurpleGrey80,
	tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
	primary = Primary,
	secondary = Secondary,
	tertiary = Pink40,
    background = BgDefault,
    surface = Color(0xFFFFFBFE),
    onPrimary = OnPrimary,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),

)

private val extendedColorScheme = ExtendedColorScheme()
val LocalExtendedColors = staticCompositionLocalOf { extendedColorScheme }

@Composable
fun ExchangeRateAppTestTheme(
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

	val extendedColors = extendedColorScheme

	CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
		MaterialTheme(
			colorScheme = colorScheme,
			typography = Typography,
			content = content
		)
	}
}