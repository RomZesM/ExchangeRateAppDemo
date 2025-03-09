package com.example.exchangerateapptest.screens.currencyscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


@Composable
fun CurrenciesScreen(
	navController: NavHostController
) {
	Column {
		Text(text = "Currencies Screen")
		IconButton(onClick = { navController.navigate("Filters") }) {
			Icon(Icons.Default.Search, contentDescription = null)
		}
	}
}