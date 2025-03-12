package com.example.exchangerateapptest.screens.currencyscreen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.exchangerateapptest.R
import com.example.exchangerateapptest.currencies.CurrencyEntry
import com.example.exchangerateapptest.screens.common.composables.CurrencyItem
import com.example.exchangerateapptest.screens.main.Screen
import com.example.exchangerateapptest.ui.theme.LocalExtendedColors

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


	CurrentScreenLayout(
		state,
		{ selectedCurrency -> viewModel.updateSelectedCurrency(selectedCurrency) },
		{ navController.navigate(Screen.Filters.route) },
		{ currency -> viewModel.toggleCurrenciesPairFavourites(currency) }
	)


}

@Composable
fun CurrentScreenLayout(
	state: State<CurrencyScreenState>,
	onSelectCurrency: (String) -> Unit,
	onFilterClick: () -> Unit,
	toggleCurrenciesFavourite: (CurrencyEntry) -> Unit
) {
	val extendedColors = LocalExtendedColors.current

	Column(
		modifier = Modifier
			.background(color = extendedColors.bgDefault)
			.fillMaxSize()
	) {
		Row(
			modifier = Modifier
				.background(color = extendedColors.bgHeader)
				.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 12.dp)
				.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			SelectableCurrencyDropdown(
				state,
				onSelectCurrency
			)
			Spacer(modifier = Modifier.width(8.dp))

			IconButton(
				onClick = { onFilterClick() },
				modifier = Modifier
					.size(48.dp)
					.border(1.dp, extendedColors.secondary, RoundedCornerShape(8.dp))
					.background(extendedColors.bgDefault)
			) {
				Icon(
					painter = painterResource(R.drawable.filter),
					contentDescription = "Filter Icon",
					tint = extendedColors.primary,
					modifier = Modifier
						.padding(12.dp)
				)
			}
		}
		Box {
			LazyColumn(
				modifier = Modifier
					.fillMaxSize()
					.padding(vertical = 16.dp, horizontal = 16.dp),
				verticalArrangement = Arrangement.spacedBy(8.dp)
			) {
				items(state.value.currencies.size) { index ->
					val currency = state.value.currencies[index]
					CurrencyItem(
						currencyTitle = currency.title,
						currencyValue = currency.value,
						isFavourite = currency.isFavourite
					) {
						toggleCurrenciesFavourite(currency)
					}
				}
			}
		}

	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectableCurrencyDropdown(
	state: State<CurrencyScreenState>,
	onSelectCurrency: (String) -> Unit
) {
	val extendedColors = LocalExtendedColors.current

	var isExpanded by remember { mutableStateOf(false) }
	val menuItems = getListOfCurrencies(state.value.currencies)

	Row(
		verticalAlignment = Alignment.CenterVertically
	) {
		ExposedDropdownMenuBox(
			expanded = isExpanded,
			onExpandedChange = { isExpanded = it }
		) {
			OutlinedTextField(
				value = state.value.selectedCurrency,
				onValueChange = {},
				readOnly = true,
				trailingIcon = {
					Icon(
						painter = painterResource(
							if (isExpanded)R.drawable.drop_up_icon else R.drawable.drop_down_icon
						),
						contentDescription = "Dropdown Icon",
						tint = extendedColors.primary
					)
				},
				textStyle = MaterialTheme.typography.bodyMedium,
				colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
					focusedBorderColor = Color.Transparent,
					unfocusedBorderColor = Color.Transparent,
					disabledBorderColor = Color.Transparent,
				),
				modifier = Modifier
					.fillMaxWidth(0.8f)
					.height(48.dp)
					.menuAnchor()
					.border(1.dp, extendedColors.secondary, RoundedCornerShape(8.dp))
					.background(extendedColors.bgDefault, RoundedCornerShape(8.dp))
			)

			ExposedDropdownMenu(
				expanded = isExpanded,
				onDismissRequest = { isExpanded = false },
				modifier = Modifier
					.heightIn(
						max = (7 * 48).dp
					)
					.align(Alignment.Bottom)
					.border(1.dp, extendedColors.secondary, RoundedCornerShape(12.dp))
					.background(extendedColors.bgDefault, RoundedCornerShape(12.dp))
			) {
				menuItems.forEach { item ->
					DropdownMenuItem(
						text = {
							Text(
								item,
								style = MaterialTheme.typography.bodyMedium,
							)
						},
						onClick = {
							onSelectCurrency(item)
							isExpanded = false
						},
						modifier = Modifier
							.background(
								if (item == state.value.selectedCurrency) extendedColors.lightPrimary
								else Color.Transparent
							)

					)
				}
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
fun CurrencyScreenPreview(showBackground: Boolean = true) {
	CurrentScreenLayout(
		state = remember {
			mutableStateOf(
				CurrencyScreenState(
					currencies = listOf(
						CurrencyEntry("USD", 1.123456, true),
						CurrencyEntry("EUR", 123.000888, true),
					)
				)
			)
		},
		onSelectCurrency = { },
		onFilterClick = { },
		toggleCurrenciesFavourite = { }

	)
}