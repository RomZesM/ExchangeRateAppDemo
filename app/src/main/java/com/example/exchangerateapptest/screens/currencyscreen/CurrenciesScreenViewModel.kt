package com.example.exchangerateapptest.screens.currencyscreen

import androidx.lifecycle.ViewModel
import com.example.exchangerateapptest.currencies.CurrenciesResponse
import com.example.exchangerateapptest.currencies.FavouriteCurrenciesPair
import com.example.exchangerateapptest.currencies.usecases.FetchLatestCurrencyUseCase
import com.example.exchangerateapptest.currencies.usecases.ObserveFavouritesCurrenciesPairsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrenciesScreenViewModel @Inject constructor(
	private val fetchLatestCurrencyUseCase: FetchLatestCurrencyUseCase,
	private val observeFavouritesCurrenciesPairsUseCase: ObserveFavouritesCurrenciesPairsUseCase
) : ViewModel() {

	val currencies = MutableStateFlow(CurrenciesResponse(emptyList()))
	val favourites = observeFavouritesCurrenciesPairsUseCase.invoke()

	suspend fun fetchLastCurrencies(selectedCurrency: String?) {
		withContext(Dispatchers.IO) {
			currencies.value = fetchLatestCurrencyUseCase.invoke(selectedCurrency)
		}
	}


}