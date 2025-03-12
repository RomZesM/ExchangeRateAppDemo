package com.example.exchangerateapptest.screens.currencyscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerateapptest.currencies.CurrencyEntry
import com.example.exchangerateapptest.currencies.FavouriteCurrenciesPair
import com.example.exchangerateapptest.currencies.usecases.FetchFavouritesCurrenciesPairsUseCase
import com.example.exchangerateapptest.currencies.usecases.FetchLatestCurrencyUseCase
import com.example.exchangerateapptest.currencies.usecases.ToggleFavouritePairIconUseCase
import com.example.exchangerateapptest.screens.filtersscreen.FiltersOptions
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

	private val _favourites = MutableStateFlow<List<FavouriteCurrenciesPair>>(emptyList())
	private val _stateFlow = MutableStateFlow(CurrencyScreenState())
	val stateFlow = _stateFlow.asStateFlow()

	suspend fun fetchLastCurrencies(selectedCurrency: String) {
		withContext(Dispatchers.IO) {
			val currencies = fetchLatestCurrencyUseCase.invoke(selectedCurrency)
			_stateFlow.value = _stateFlow.value.copy(currencies = currencies)
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

	fun toggleCurrenciesPairFavourites(targetPair: CurrencyEntry) {
		viewModelScope.launch(Dispatchers.IO) {
			val pair = FavouriteCurrenciesPair(
				id = _stateFlow.value.selectedCurrency + "/" + targetPair.title,
				baseCurrency = _stateFlow.value.selectedCurrency,
				targetCurrency = targetPair.title,
				value = targetPair.value
			)
			toggleFavouritePairIconUseCase.invoke(pair)
			fetchFavouriteCurrenciesPairs(_stateFlow.value.selectedCurrency)
			updateFavouriteStatus(_stateFlow.value.selectedCurrency)
		}
	}

	private fun updateFavouriteStatus(selectedValue: String) {
		val updatedList = _stateFlow.value.currencies.map {
			val id = selectedValue + "/" + it.title
			val isFavourite = _favourites.value.any { fav ->
				fav.id == id
			}
			it.copy(isFavourite = isFavourite)
		}
		_stateFlow.value = _stateFlow.value.copy(currencies = updatedList)
	}

	fun updateSelectedCurrency(selectedCurrency: String) {
		_stateFlow.value = _stateFlow.value.copy(selectedCurrency = selectedCurrency)

	}

	fun updateFilterOptions(filter: FiltersOptions) {
		_stateFlow.value = _stateFlow.value.copy(currentFiltersOptions = filter)
	}

	fun sortCurrencies() {

		val sortedList = when (_stateFlow.value.currentFiltersOptions) {
			FiltersOptions.SORTING_TITLE_ASC ->
				_stateFlow.value.currencies.sortedBy { it.title }

			FiltersOptions.SORTING_TITLE_DESC ->
				_stateFlow.value.currencies.sortedByDescending { it.title }

			FiltersOptions.SORTING_VALUE_ASC ->
				_stateFlow.value.currencies.sortedBy { it.value }

			FiltersOptions.SORTING_VALUE_DESC ->
				_stateFlow.value.currencies.sortedByDescending { it.value }
		}
		_stateFlow.value = _stateFlow.value.copy(currencies = sortedList)
	}


}

