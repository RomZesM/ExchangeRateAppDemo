package com.example.exchangerateapptest.screens.currencyscreen

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.exchangerateapptest.currencies.CurrencyEntry
import com.example.exchangerateapptest.screens.common.composables.CurrencyItem

@Composable
fun CurrenciesScreen(
	navController: NavHostController
) {
	val viewModel: CurrenciesScreenViewModel = hiltViewModel()
	val currencies = viewModel.currencies.collectAsState()
	val selectedCurrency = viewModel.selectedCurrency.collectAsState()

	LaunchedEffect(Unit) {
		viewModel.fetchFavouriteCurrenciesPairs(selectedCurrency.value)
	}

	LaunchedEffect(selectedCurrency.value) {
		viewModel.fetchLastCurrencies(selectedCurrency.value)
	}

	Column {
		Row {
			SelectableCurrencyDropdown(
				currencies.value.data,
				selectedCurrency.value
			) { selectedCurrency -> viewModel.updateSelectedCurrency(selectedCurrency) }

			IconButton(onClick = { navController.navigate("Filters") }) {
				Icon(Icons.Default.Search, contentDescription = null)
			}
		}
			Box() {
				LazyColumn(
					modifier = Modifier
						.fillMaxSize()
						.padding(vertical = 5.dp),
					verticalArrangement = Arrangement.spacedBy(20.dp),
					contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp)
				) {
					items(currencies.value.data.size) { index ->
						val currency = currencies.value.data[index]
						CurrencyItem(
							currencyTitle = currency.title,
							currencyValue = currency.value,
							isFavourite = currency.isFavourite
						) {
							viewModel.toggleCurrenciesPairFavourites(
								selectedCurrency.value,
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
