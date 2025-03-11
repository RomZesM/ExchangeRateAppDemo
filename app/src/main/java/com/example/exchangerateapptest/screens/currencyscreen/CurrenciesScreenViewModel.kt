package com.example.exchangerateapptest.screens.currencyscreen

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

	val currentFiltersOptions = MutableStateFlow(FiltersOptions.SORTING_TITLE_ASC)


	suspend fun fetchLastCurrencies(selectedCurrency: String) {
		withContext(Dispatchers.IO) {
			_currencies.value = fetchLatestCurrencyUseCase.invoke(selectedCurrency)
			updateFavouriteStatus(selectedCurrency)
			sortCurrencies()
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
			val id = selectedValue + "/" + it.title
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

	fun updateFilterOptions(filter: FiltersOptions) {
		currentFiltersOptions.value = filter
	}

	fun sortCurrencies() {

		val sortedList = when (currentFiltersOptions.value) {
			FiltersOptions.SORTING_TITLE_ASC ->
				_currencies.value.data.sortedBy { it.title }

			FiltersOptions.SORTING_TITLE_DESC ->
				_currencies.value.data.sortedByDescending { it.title }

			FiltersOptions.SORTING_VALUE_ASC ->
				_currencies.value.data.sortedBy { it.value }

			FiltersOptions.SORTING_VALUE_DESC ->
				_currencies.value.data.sortedByDescending { it.value }
		}
		_currencies.value = _currencies.value.copy(data = sortedList)
	}


}

