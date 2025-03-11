package com.example.exchangerateapptest.screens.currencyscreen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.exchangerateapptest.R
import com.example.exchangerateapptest.currencies.CurrencyEntry
import com.example.exchangerateapptest.screens.common.composables.CurrencyItem
import com.example.exchangerateapptest.screens.main.Screen

@Composable
fun CurrenciesScreen(
	navController: NavHostController,
	viewModel: CurrenciesScreenViewModel
) {

	val state = viewModel.stateFlow.collectAsState()

	LaunchedEffect(Unit) {
		viewModel.fetchFavouriteCurrenciesPairs(state.value.selectedCurrency)
	}

	LaunchedEffect(state.value.selectedCurrency) {
		viewModel.fetchLastCurrencies(state.value.selectedCurrency)
	}

	LaunchedEffect(state.value.currentFiltersOptions) {
		viewModel.sortCurrencies()
	}

	Column(modifier = Modifier.padding(vertical = 10.dp)) {
		Row(
			modifier = Modifier.padding(vertical = 10.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			SelectableCurrencyDropdown(
				state.value.currencies,
				state.value.selectedCurrency
			) { selectedCurrency -> viewModel.updateSelectedCurrency(selectedCurrency) }

			IconButton(onClick = {
				navController.navigate(Screen.Filters.route)
			}) {
				Icon(Icons.Default.Search, contentDescription = null)
			}
		}
		Box {
			LazyColumn(
				modifier = Modifier
					.fillMaxSize()
					.padding(vertical = 5.dp),
				verticalArrangement = Arrangement.spacedBy(20.dp),
				contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp)
			) {
				items(state.value.currencies.size) { index ->
					val currency = state.value.currencies[index]
					CurrencyItem(
						currencyTitle = currency.title,
						currencyValue = currency.value,
						isFavourite = currency.isFavourite
					) {
						viewModel.toggleCurrenciesPairFavourites(
							currency
						)
					}
				}
			}
		}

	}

}

@Composable
fun SelectableCurrencyDropdown(
	currencies: List<CurrencyEntry>,
	selectedCurrency: String,
	onSelectCurrency: (String) -> Unit
) {
	var expanded by remember { mutableStateOf(false) }
	val menuItems = getListOfCurrencies(currencies)

	Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
		Button(onClick = { expanded = true }) {
			Text(text = selectedCurrency)
		}

		DropdownMenu(
			expanded = expanded,
			onDismissRequest = { expanded = false }
		) {
			menuItems.forEach { item ->
				DropdownMenuItem(
					text = { Text(item) },
					onClick = {
						onSelectCurrency(item)
						expanded = false
					}
				)
			}
		}
	}
}

private fun getListOfCurrencies(currencies: List<CurrencyEntry>): List<String> {
	val currenciesOptions = mutableListOf<String>()
	for (currency in currencies) {
		currenciesOptions.add(currency.title)
	}
	return currenciesOptions
}

enum class FiltersOptions(@StringRes val stringId: Int) {
	SORTING_TITLE_ASC(R.string.code_a_z),
	SORTING_TITLE_DESC(R.string.code_z_a),
	SORTING_VALUE_ASC(R.string.quote_asc),
	SORTING_VALUE_DESC(R.string.quote_desc)
}


@Preview
@Composable
fun GreetingPreview() {
	CurrenciesScreen(
		navController = rememberNavController(),
		viewModel = hiltViewModel()
	)
}