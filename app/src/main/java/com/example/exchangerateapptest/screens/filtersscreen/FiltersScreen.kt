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
import com.example.exchangerateapptest.R


@Composable
fun FiltersScreen() {
	val options = listOf(
		stringResource(R.string.code_a_z),
		stringResource(R.string.code_z_a),
		stringResource(R.string.quote_asc),
		stringResource(R.string.quote_desc)
	)

	var selectedOption by remember { mutableStateOf<String?>(null) }

	Column {
		options.forEach { option ->
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
					.clickable { selectedOption = option }
					.padding(8.dp)
			) {
				Text(text = option, modifier = Modifier.padding(start = 8.dp))

				Checkbox(
					checked = selectedOption == option,
					onCheckedChange = { selectedOption = option }
				)
			}
		}
		Button({}) {
			Text(text = stringResource(R.string.apply))
		}
	}

}