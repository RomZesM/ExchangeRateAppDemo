package com.example.exchangerateapptest.screens.currencyscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.exchangerateapptest.screens.common.composables.CurrencyItem


@Composable
fun CurrenciesScreen(
	viewModel: CurrenciesScreenViewModel = hiltViewModel(),
	navController: NavHostController
) {

	val currencies = viewModel.currencies.collectAsState()

	LaunchedEffect(Unit) {
		viewModel.fetchLastCurrencies()
	}

	Column {
		IconButton(onClick = { navController.navigate("Filters") }) {
			Icon(Icons.Default.Search, contentDescription = null)
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
						currencyTitle = currency.first,
						currencyValue = currency.second,
					)
					if (index < currencies.value.data.size - 1) {
						HorizontalDivider(
							modifier = Modifier
								.padding(top = 20.dp),
							thickness = 2.dp
						)
					}
				}
			}
		}
	}


}