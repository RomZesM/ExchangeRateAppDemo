package com.example.exchangerateapptest.screens.currencyscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.exchangerateapptest.currencies.usecases.CurrenciesResponse
import com.example.exchangerateapptest.currencies.usecases.FetchLatestCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrenciesScreenViewModel @Inject constructor(
	private val fetchLatestCurrencyUseCase: FetchLatestCurrencyUseCase
) : ViewModel() {

	val currencies = MutableStateFlow(CurrenciesResponse(emptyMap()))

	suspend fun fetchLastCurrencies() {
		withContext(Dispatchers.IO) {
			currencies.value = fetchLatestCurrencyUseCase.invoke()
		}
	}
}