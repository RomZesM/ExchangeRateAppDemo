package com.example.exchangerateapptest.screens.favouritescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.exchangerateapptest.screens.common.composables.CurrencyItem

@Composable
fun FavouritesScreen() {

	val viewModel: FavouriteScreenViewModel = hiltViewModel()
	val favouriteCurrencies = viewModel.favourites.collectAsState()

	LaunchedEffect(Unit) {
		viewModel.fetchFavouriteCurrenciesPairs()
	}


	Box() {
		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
				.padding(vertical = 5.dp),
			verticalArrangement = Arrangement.spacedBy(20.dp),
			contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp)
		) {
			items(favouriteCurrencies.value.size) { index ->
				val favCurrency = favouriteCurrencies.value[index]
				CurrencyItem(
					currencyTitle = favCurrency.id,
					currencyValue = favCurrency.value,
					isFavourite = favCurrency.isFavourite
				) {

				}
			}
		}
	}

}