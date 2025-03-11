package com.example.exchangerateapptest.screens.currencyscreen

import com.example.exchangerateapptest.currencies.CurrencyEntry
import com.example.exchangerateapptest.screens.common.composables.Constants

data class CurrencyScreenState(
	var currentFiltersOptions: FiltersOptions = FiltersOptions.SORTING_TITLE_ASC,
	var selectedCurrency: String = Constants.DEFAULT_CURRENCY,
	val currencies: List<CurrencyEntry> = emptyList()
)
