package com.example.exchangerateapptest.currencies.usecases

import com.example.exchangerateapptest.common.database.FavouriteCurrenciesPairDAO
import com.example.exchangerateapptest.currencies.FavouriteCurrenciesPair
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveFavouritesCurrenciesPairsUseCase @Inject constructor(
	private val favouriteCurrenciesPairDAO: FavouriteCurrenciesPairDAO
) {
	operator fun invoke(): Flow<List<FavouriteCurrenciesPair>> {
		return favouriteCurrenciesPairDAO.observe()
			.map { pairEntityList ->
				pairEntityList.map { entity ->
					FavouriteCurrenciesPair(
						id = entity.id,
						baseCurrency = entity.baseCurrency,
						targetCurrency = entity.targetCurrency,
						value = entity.value,
						timestamp = entity.timestamp
					)
				}
			}
	}
}