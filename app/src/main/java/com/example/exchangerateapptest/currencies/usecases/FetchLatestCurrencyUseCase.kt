package com.example.exchangerateapptest.currencies.usecases


import android.util.Log
import com.example.exchangerateapptest.common.extensions.roundToSixDigits
import com.example.exchangerateapptest.currencies.CurrencyEntry
import com.example.exchangerateapptest.networking.FreeCurrencyApi
import javax.inject.Inject

class FetchLatestCurrencyUseCase @Inject constructor(
	private val freeCurrencyApi: FreeCurrencyApi
) {

	suspend fun invoke(selectedCurrency: String?): List<CurrencyEntry> {

		return try {
			val response = freeCurrencyApi.getLatestRates(selectedCurrency)

			if (response.isSuccessful) {
				response.body()?.data?.map { (key, value) ->
					CurrencyEntry(title = key, value = value.roundToSixDigits())
				} ?: emptyList()
			} else {
				Log.e("Fetching currencies error", "API request failed: ${response.code()}")
				emptyList()
			}
		} catch (e: Exception) {
			Log.e("Fetching currencies error:", e.message.toString())
			emptyList()
		}
	}

}