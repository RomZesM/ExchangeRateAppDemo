package com.example.exchangerateapptest.screens.filtersscreen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.exchangerateapptest.R
import com.example.exchangerateapptest.screens.currencyscreen.CurrenciesScreenViewModel
import com.example.exchangerateapptest.screens.currencyscreen.CurrencyScreenState
import com.example.exchangerateapptest.ui.theme.LocalExtendedColors

@Composable
fun FiltersScreen(
	sharedViewModel: CurrenciesScreenViewModel,
	navController: NavHostController
) {

	val filterOptions = listOf(
		FiltersOptions.SORTING_TITLE_ASC,
		FiltersOptions.SORTING_TITLE_DESC,
		FiltersOptions.SORTING_VALUE_ASC,
		FiltersOptions.SORTING_VALUE_DESC
	)

	val state = sharedViewModel.stateFlow.collectAsState()



	FilterScreenLayout(filterOptions, state) { selectedOption ->
		sharedViewModel.updateFilterOptions(selectedOption)
		navController.popBackStack()
	}

}

@Composable
fun FilterScreenLayout(
	filterOptions: List<FiltersOptions>,
	state: State<CurrencyScreenState>,
	onApplyClick: (FiltersOptions) -> Unit,
) {

	var selectedOption by remember { mutableStateOf(state.value.currentFiltersOptions) }
	val extendedColors = LocalExtendedColors.current

	Column(
		modifier = Modifier
			.background(color = extendedColors.bgDefault)
			.verticalScroll(rememberScrollState())
			.fillMaxSize()

	) {
		filterOptions.forEach { option ->
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween,
				modifier = Modifier
					.fillMaxWidth()
					.clickable { selectedOption = option }
					.padding(8.dp)
			) {
				Text(
					text = stringResource(option.stringId),
					style = MaterialTheme.typography.bodyMedium,
					modifier = Modifier.padding(start = 8.dp)
				)

				RadioButton(
					selected = selectedOption == option,
					onClick = { selectedOption = option },
					colors = RadioButtonDefaults.colors(
						selectedColor = extendedColors.primary, // Custom color when selected
						unselectedColor = extendedColors.secondary // Custom color when not selected
					)
				)
			}
		}
		Spacer(modifier = Modifier.weight(1f))
		Button(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			colors = ButtonColors(
				containerColor = extendedColors.primary,
				contentColor = extendedColors.primary,
				disabledContainerColor = extendedColors.secondary,
				disabledContentColor = extendedColors.secondary
			),
			onClick = {
				onApplyClick(selectedOption)
			}
		) {
			Text(
				text = stringResource(R.string.apply),
				style = MaterialTheme.typography.bodySmall,
				color = extendedColors.onPrimary,
				modifier = Modifier
					.padding(10.dp)
			)
		}
	}

}

enum class FiltersOptions(@StringRes val stringId: Int) {
	SORTING_TITLE_ASC(R.string.code_a_z),
	SORTING_TITLE_DESC(R.string.code_z_a),
	SORTING_VALUE_ASC(R.string.quote_asc),
	SORTING_VALUE_DESC(R.string.quote_desc)
}

@Preview
@Composable
fun FilterScreenLayoutPreview() {
	FilterScreenLayout(
		filterOptions = listOf(
			FiltersOptions.SORTING_TITLE_ASC,
			FiltersOptions.SORTING_TITLE_DESC,
			FiltersOptions.SORTING_VALUE_ASC,
			FiltersOptions.SORTING_VALUE_DESC
		),
		state = remember {
			mutableStateOf(
				CurrencyScreenState(
					currentFiltersOptions = FiltersOptions.SORTING_TITLE_ASC
				),

				)
		},
		onApplyClick = {}
	)
}