package com.example.exchangerateapptest.currencies


data class CurrencyEntry(
	val title: String,
	val value: String,
	val isFavourite: Boolean = false
)
