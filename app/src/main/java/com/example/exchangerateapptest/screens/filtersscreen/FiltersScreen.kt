package com.example.exchangerateapptest.screens.filtersscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.exchangerateapptest.R
import com.example.exchangerateapptest.screens.currencyscreen.CurrenciesScreenViewModel
import com.example.exchangerateapptest.screens.currencyscreen.FiltersOptions


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

	var selectedOption by remember { mutableStateOf(sharedViewModel.currentFiltersOptions.value) }

	Column {
		filterOptions.forEach { option ->
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
					.clickable { selectedOption = option }
					.padding(8.dp)
			) {
				Text(text = option.name, modifier = Modifier.padding(start = 8.dp))

				Checkbox(
					checked = selectedOption == option,
					onCheckedChange = { selectedOption = option }
				)
			}
		}
		Button({
			sharedViewModel.updateFilterOptions(selectedOption)
			navController.popBackStack()
		}) {
			Text(text = stringResource(R.string.apply))
		}
	}

}