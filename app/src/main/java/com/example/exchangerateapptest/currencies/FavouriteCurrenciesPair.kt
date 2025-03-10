package com.example.exchangerateapptest.currencies

data class FavouriteCurrenciesPair(
	val id: String,
	val baseCurrency: String,
	val targetCurrency: String,
	val value: Double,
	val isFavourite: Boolean = false,
	val timestamp: Long = System.currentTimeMillis()
)

