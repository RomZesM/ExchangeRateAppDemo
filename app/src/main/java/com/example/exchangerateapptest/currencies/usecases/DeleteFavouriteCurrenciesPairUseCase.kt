package com.example.exchangerateapptest.currencies.usecases

import android.util.Log
import com.example.exchangerateapptest.common.database.FavouriteCurrenciesPairDAO
import javax.inject.Inject

class DeleteFavouriteCurrenciesPairUseCase @Inject constructor(
	private val favouriteCurrenciesPairDAO: FavouriteCurrenciesPairDAO
) {
	suspend fun invoke(id: String) {
		try {
			favouriteCurrenciesPairDAO.delete(id)
		}
		catch (e: Exception){
			Log.e("DB Error:", "Error deleting favourite pair: ${e.message}")
		}
	}
}