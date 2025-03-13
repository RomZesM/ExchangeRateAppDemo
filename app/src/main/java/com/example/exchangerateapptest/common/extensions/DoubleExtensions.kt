package com.example.exchangerateapptest.common.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.roundToSixDigits(): Double {
	return BigDecimal(this)
		.setScale(6, RoundingMode.HALF_UP)
		.toDouble()
}