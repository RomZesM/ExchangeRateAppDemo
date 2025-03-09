package com.example.exchangerateapptest.currencies.usecases


import android.util.Log
import com.example.exchangerateapptest.networking.FreeCurrencyApi
import javax.inject.Inject

class FetchLatestCurrencyUseCase @Inject constructor(
	private val freeCurrencyApi: FreeCurrencyApi
) {

	private var currencies = emptyMap<String, Double>()

	suspend fun invoke(): CurrenciesResponse {

		val map = mutableMapOf<String, Double>()
		try {
			val response = freeCurrencyApi.getLatestRates()
			if (response.isSuccessful && response.body() != null) {
				response.body()?.data?.map { currenciesResponseSchema ->
					map.put(currenciesResponseSchema.key, currenciesResponseSchema.value)
				}
				currencies = map
			}
		} catch (e: Exception) {
			Log.e("Fetching currencies", e.toString())
		}
		return CurrenciesResponse(currencies)
	}

}