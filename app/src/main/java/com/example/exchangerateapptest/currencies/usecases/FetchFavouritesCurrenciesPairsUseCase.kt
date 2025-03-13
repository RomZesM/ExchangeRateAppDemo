package com.example.exchangerateapptest.currencies.usecases

import android.util.Log
import com.example.exchangerateapptest.common.database.FavouriteCurrenciesPairDAO
import com.example.exchangerateapptest.common.extensions.roundToSixDigits
import com.example.exchangerateapptest.currencies.FavouriteCurrenciesPair
import javax.inject.Inject

class FetchFavouritesCurrenciesPairsUseCase @Inject constructor(
	private val favouriteCurrenciesPairDAO: FavouriteCurrenciesPairDAO
) {

	suspend operator fun invoke(): List<FavouriteCurrenciesPair> {
		val favCurrenciesList = mutableListOf<FavouriteCurrenciesPair>()
		try {
			val result = favouriteCurrenciesPairDAO.getAll()
			if (result.isNotEmpty()) {
				result.map {
					favCurrenciesList.add(
						FavouriteCurrenciesPair(
							id = it.id,
							baseCurrency = it.baseCurrency,
							targetCurrency = it.targetCurrency,
							value = it.value.roundToSixDigits(),
							timestamp = it.timestamp
						)
					)
				}
			}

		} catch (e: Exception) {
			Log.e("Fetching currencies from DB error:", e.toString())
		}
		return favCurrenciesList
	}
}