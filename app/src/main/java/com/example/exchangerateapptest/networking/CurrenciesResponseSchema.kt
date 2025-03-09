package com.example.exchangerateapptest.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrenciesResponseSchema(
	val data: Map<String, Double>?
)