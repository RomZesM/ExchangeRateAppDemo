package com.example.exchangerateapptest.screens.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.exchangerateapptest.screens.currencyscreen.CurrenciesScreen
import com.example.exchangerateapptest.screens.currencyscreen.CurrenciesScreenViewModel
import com.example.exchangerateapptest.screens.favouritescreen.FavouritesScreen
import com.example.exchangerateapptest.screens.filtersscreen.FiltersScreen
import com.example.exchangerateapptest.ui.theme.LocalExtendedColors


@Composable
fun MainScreen() {
	val navController: NavHostController = rememberNavController()
	val sharedViewModel: CurrenciesScreenViewModel = hiltViewModel()

	val listOfScreensWithBottomAppBar = listOf(
		Screen.Currencies.route,
		Screen.Favourites.route
	)
	val extendedColors = LocalExtendedColors.current

	Scaffold(
		topBar = {
			MyTopAppBar(
				navController = navController
			)
		},
		content = { padding ->
			MainScreenContent(
				sharedViewModel = sharedViewModel,
				padding = padding,
				navController = navController,
			)
		},
		bottomBar = {
			if (currentRoute(navController) in listOfScreensWithBottomAppBar) {
				Column {
					HorizontalDivider(thickness = 1.dp, color = extendedColors.outline)
					BottomAppBar(containerColor = extendedColors.bgDefault) {
						MyBottomTabsBar(
							navController = navController
						)
					}
				}

			}

		},
	)
}

@Composable
fun MainScreenContent(
	padding: PaddingValues,
	navController: NavHostController,
	sharedViewModel: CurrenciesScreenViewModel,
) {
	Surface(
		modifier = Modifier
			.padding(padding)
	) {
		NavHost(
			modifier = Modifier.fillMaxSize(),
			navController = navController,
			enterTransition = { fadeIn(animationSpec = tween(200)) },
			exitTransition = { fadeOut(animationSpec = tween(200)) },
			startDestination = "Currencies",
		) {
			composable(Screen.Currencies.route) {
				CurrenciesScreen(
					navController = navController,
					viewModel = sharedViewModel
				)
			}
			composable(Screen.Favourites.route) {
				FavouritesScreen()
			}
			composable(Screen.Filters.route) {
				FiltersScreen(
					navController = navController,
					sharedViewModel = sharedViewModel
				)
			}
		}
	}
}

@Composable
fun currentRoute(navController: NavHostController): String? {
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	return navBackStackEntry?.destination?.route
}

sealed class Screen(val route: String) {
	object Currencies : Screen("Currencies")
	object Favourites : Screen("Favourites")
	object Filters : Screen("Filters")
}
