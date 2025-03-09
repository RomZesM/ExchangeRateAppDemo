package com.example.exchangerateapptest.screens.main

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
	navController: NavHostController
) {
	val currentRouteTitle = currentRoute(navController) ?: ""

	CenterAlignedTopAppBar(
		title = {
			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = currentRouteTitle,
					color = Color.White
				)
			}
		},
		colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
	)
}
