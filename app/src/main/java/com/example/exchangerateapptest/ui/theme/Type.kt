package com.example.exchangerateapptest.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.exchangerateapptest.R

val InterFont = FontFamily(
	Font(R.font.inter_medium, FontWeight.Medium),
	Font(R.font.inter_semibold, FontWeight.SemiBold),
	Font(R.font.inter_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
	headlineLarge = TextStyle(
		fontFamily = InterFont,
		fontWeight = FontWeight.Bold,
		fontSize = 22.sp,
		lineHeight = 28.sp,
		letterSpacing = 0.sp
	),
    bodySmall = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
	bodyMedium = TextStyle(
		fontFamily = InterFont,
		fontWeight = FontWeight.Medium,
		fontSize = 16.sp,
		lineHeight = 20.sp,
		letterSpacing = 0.sp
	),
    bodyLarge = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
	labelSmall = TextStyle(
		fontFamily = InterFont,
		fontWeight = FontWeight.SemiBold,
		fontSize = 12.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.sp
	)


)