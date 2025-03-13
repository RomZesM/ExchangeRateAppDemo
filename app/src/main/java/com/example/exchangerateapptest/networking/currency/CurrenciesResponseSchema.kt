package com.example.exchangerateapptest.networking.currency

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrenciesResponseSchema(
	val data: Map<String, Double>?
)