package com.example.exchangerateapptest.currencies.usecases


import android.util.Log
import com.example.exchangerateapptest.currencies.CurrencyEntry
import com.example.exchangerateapptest.networking.FreeCurrencyApi
import javax.inject.Inject

class FetchLatestCurrencyUseCase @Inject constructor(
	private val freeCurrencyApi: FreeCurrencyApi
) {

	suspend fun invoke(selectedCurrency: String?): List<CurrencyEntry> {
		val list = mutableListOf<CurrencyEntry>()
		try {
			val response = freeCurrencyApi.getLatestRates(selectedCurrency)
			if (response.isSuccessful && response.body() != null) {
				response.body()?.data?.map { currenciesResponseSchema ->
					list.add(
						CurrencyEntry(
							title = currenciesResponseSchema.key,
							value = currenciesResponseSchema.value
						)
					)
				}
			}
		} catch (e: Exception) {
			Log.e("Fetching currencies error:", e.toString())
		}
		return list
	}

}