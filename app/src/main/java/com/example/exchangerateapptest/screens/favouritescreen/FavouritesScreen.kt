package com.example.exchangerateapptest.screens.favouritescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.exchangerateapptest.currencies.FavouriteCurrenciesPair
import com.example.exchangerateapptest.screens.common.composables.CurrencyItem
import com.example.exchangerateapptest.ui.theme.LocalExtendedColors

@Composable
fun FavouritesScreen() {

	val viewModel: FavouriteScreenViewModel = hiltViewModel()
	val favouriteCurrencies = viewModel.favourites.collectAsState(emptyList())

	FavouritesScreenLayout(
		favouriteCurrencies
	) { favCurrency ->
		viewModel.toggleCurrenciesPairFavourites(favCurrency)
	}

}

@Composable
fun FavouritesScreenLayout(
	favouriteCurrencies: State<List<FavouriteCurrenciesPair>>,
	onFavouriteClick: (FavouriteCurrenciesPair) -> Unit
) {
	val extendedColors = LocalExtendedColors.current

	Box(modifier = Modifier.background(extendedColors.bgDefault)) {
		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
				.padding(top = 16.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp)
		) {
			items(favouriteCurrencies.value.size) { index ->
				val favCurrency = favouriteCurrencies.value[index]
				CurrencyItem(
					currencyTitle = favCurrency.id,
					currencyValue = favCurrency.value,
					isFavourite = true
				) {
					onFavouriteClick(favCurrency)
				}
			}
		}
	}
}

@Preview
@Composable
fun FavouritesScreenLayoutPreview() {
	val mockCurrencies = remember {
		mutableStateOf(
			listOf(
				FavouriteCurrenciesPair(
					id = "EUR/PLN",
					baseCurrency = "EUR",
					targetCurrency = "PLN",
					value = "2.254654",
					isFavourite = true,
					timestamp = 1234567890
				),
			)
		)
	}

	FavouritesScreenLayout(
		favouriteCurrencies = mockCurrencies,
		onFavouriteClick = {}
	)
}