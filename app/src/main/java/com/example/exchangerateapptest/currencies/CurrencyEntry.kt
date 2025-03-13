package com.example.exchangerateapptest.currencies


data class CurrencyEntry(
	val title: String,
	val value: Double,
	val isFavourite: Boolean = false
)
