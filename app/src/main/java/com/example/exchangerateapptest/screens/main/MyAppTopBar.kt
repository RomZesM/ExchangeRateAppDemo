package com.example.exchangerateapptest.screens.main

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.exchangerateapptest.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
	navController: NavHostController
) {
	val currentRoute = currentRoute(navController) ?: ""
	val currentRouteTitle = when (
		currentRoute
	) {
		Screen.Currencies.route -> stringResource(R.string.screen_title_currencies)
		Screen.Favourites.route -> stringResource(R.string.screen_title_favourites)
		Screen.Filters.route -> stringResource(R.string.screen_title_filters)
		else -> ""
	}

	val listOfScreensWithBackArrow = listOf(
		Screen.Filters.route
	)

	CenterAlignedTopAppBar(
		title = {
			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				if (currentRoute in listOfScreensWithBackArrow) {
					IconButton(
						onClick = { navController.popBackStack() },
						content = { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }
					)
				}

				Text(
					text = currentRouteTitle,
					color = Color.White
				)
			}
		},
		colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
	)
}
