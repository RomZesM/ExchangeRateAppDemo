package com.example.exchangerateapptest.networking

import retrofit2.Response
import retrofit2.http.GET

interface FreeCurrencyApi {

	@GET("/v1/latest?apikey=$FREECURRENCY_API_KEY")
	suspend fun getLatestRates(): Response<CurrenciesResponseSchema?>

	companion object {
		const val FREECURRENCY_API_KEY = "fca_live_SbFLL7pODSrSsiZhlTLmPSyUy5NHuafgECGq16y5"

	}
}