package com.example.exchangerateapptest.networking

import com.example.exchangerateapptest.networking.currency.CurrenciesResponseSchema
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FreeCurrencyApi {

	@GET("/v1/latest?apikey=$FREECURRENCY_API_KEY")
	suspend fun getLatestRates(@Query ("base_currency") baseCurrency: String?): Response<CurrenciesResponseSchema?>

	companion object {
		const val FREECURRENCY_API_KEY = "fca_live_SbFLL7pODSrSsiZhlTLmPSyUy5NHuafgECGq16y5"
	}
}