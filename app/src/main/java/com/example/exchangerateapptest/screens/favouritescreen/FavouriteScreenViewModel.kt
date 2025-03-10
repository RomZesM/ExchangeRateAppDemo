package com.example.exchangerateapptest.screens.favouritescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerateapptest.currencies.FavouriteCurrenciesPair
import com.example.exchangerateapptest.currencies.usecases.DeleteFavouriteCurrenciesPairUseCase
import com.example.exchangerateapptest.currencies.usecases.ObserveFavouritesCurrenciesPairsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteScreenViewModel @Inject constructor(
	private val observeFavouritesCurrenciesPairsUseCase: ObserveFavouritesCurrenciesPairsUseCase,
	private val deleteFavouriteCurrenciesPairUseCase: DeleteFavouriteCurrenciesPairUseCase
) : ViewModel() {

	val favourites = observeFavouritesCurrenciesPairsUseCase.invoke()

	fun toggleCurrenciesPairFavourites(favCurrency: FavouriteCurrenciesPair) {
		viewModelScope.launch(Dispatchers.IO) {
			deleteFavouriteCurrenciesPairUseCase.invoke(favCurrency.id)
		}
	}
}