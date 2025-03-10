package com.example.exchangerateapptest.screens.common.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CurrencyItem(
	currencyTitle: String,
	currencyValue: Double,
	isFavourite: Boolean,
	favToggle: () -> Unit
) {

	Box(
		modifier = Modifier
			.wrapContentHeight()
			.fillMaxWidth()
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
		) {
			Text(
				modifier = Modifier
					.weight(1.8f),
				text = currencyTitle,
				style = MaterialTheme.typography.bodyLarge
			)

			Text(
				modifier = Modifier
					.weight(1.8f),
				text = currencyValue.toString(),
				style = MaterialTheme.typography.bodyLarge
			)

			val icon = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder

			Button(onClick = { favToggle() }) {
				Icon(
					icon,
					contentDescription = null
				)
			}
		}
	}
}