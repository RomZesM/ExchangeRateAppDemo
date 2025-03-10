package com.example.exchangerateapptest.screens.currencyscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerateapptest.currencies.CurrenciesResponse
import com.example.exchangerateapptest.currencies.CurrencyEntry
import com.example.exchangerateapptest.currencies.FavouriteCurrenciesPair
import com.example.exchangerateapptest.currencies.usecases.FetchFavouritesCurrenciesPairsUseCase
import com.example.exchangerateapptest.currencies.usecases.FetchLatestCurrencyUseCase
import com.example.exchangerateapptest.currencies.usecases.ToggleFavouritePairIconUseCase
import com.example.exchangerateapptest.screens.common.composables.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrenciesScreenViewModel @Inject constructor(
	private val fetchLatestCurrencyUseCase: FetchLatestCurrencyUseCase,
	private val fetchFavouritesCurrenciesPairsUseCase: FetchFavouritesCurrenciesPairsUseCase,
	private val toggleFavouritePairIconUseCase: ToggleFavouritePairIconUseCase
) : ViewModel() {

	private val _currencies = MutableStateFlow(CurrenciesResponse(emptyList()))
	val currencies = _currencies.asStateFlow()
	private val _favourites = MutableStateFlow<List<FavouriteCurrenciesPair>>(emptyList())
	val favourites = _favourites.asStateFlow()
	private val _selectedCurrency = MutableStateFlow(Constants.DEFAULT_CURRENCY)
	val selectedCurrency = _selectedCurrency.asStateFlow()


	suspend fun fetchLastCurrencies(selectedCurrency: String) {
		withContext(Dispatchers.IO) {
			_currencies.value = fetchLatestCurrencyUseCase.invoke(selectedCurrency)
			updateFavouriteStatus(selectedCurrency)

		}
	}

	fun fetchFavouriteCurrenciesPairs(selectedCurrency: String) {
		viewModelScope.launch(Dispatchers.IO) {
			_favourites.value = fetchFavouritesCurrenciesPairsUseCase.invoke()
			updateFavouriteStatus(selectedCurrency)
		}
	}


	fun toggleCurrenciesPairFavourites(selectedCurrency: String, targetPair: CurrencyEntry) {
		viewModelScope.launch(Dispatchers.IO) {
			val pair = FavouriteCurrenciesPair(
				id = selectedCurrency + "/" + targetPair.title,
				baseCurrency = selectedCurrency,
				targetCurrency = targetPair.title,
				value = targetPair.value
			)
			toggleFavouritePairIconUseCase.invoke(pair)
			fetchFavouriteCurrenciesPairs(selectedCurrency)
			updateFavouriteStatus(selectedCurrency)
		}
	}


	private fun updateFavouriteStatus(selectedValue: String) {
		val updatedList = _currencies.value.data.map {
			val id = selectedValue + "/" + it.title // USD/EUR
			val isFavourite = _favourites.value.any { fav ->
				fav.id == id
			}
			it.copy(isFavourite = isFavourite)
		}
		_currencies.value = _currencies.value.copy(data = updatedList)
	}

	fun updateSelectedCurrency(selectedCurrency: String) {
		_selectedCurrency.value = selectedCurrency
	}

}