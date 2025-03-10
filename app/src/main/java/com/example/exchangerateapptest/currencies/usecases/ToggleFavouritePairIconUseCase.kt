package com.example.exchangerateapptest.currencies.usecases

import com.example.exchangerateapptest.common.database.FavouriteCurrenciesPairDAO
import com.example.exchangerateapptest.common.database.FavouriteCurrenciesPairEntity
import com.example.exchangerateapptest.currencies.FavouriteCurrenciesPair
import javax.inject.Inject

	class ToggleFavouritePairIconUseCase @Inject constructor(
		private val favouriteCurrenciesPairDAO: FavouriteCurrenciesPairDAO
) {
	suspend operator fun invoke(favouritePair: FavouriteCurrenciesPair) {
		if (favouriteCurrenciesPairDAO.getById(favouritePair.id) != null) {
			favouriteCurrenciesPairDAO.delete(favouritePair.id)
		} else {
			favouriteCurrenciesPairDAO.upsert(
				FavouriteCurrenciesPairEntity(
					id = favouritePair.id,
					baseCurrency = favouritePair.baseCurrency,
					targetCurrency = favouritePair.targetCurrency,
					value = favouritePair.value
				)
			)
		}
	}

}