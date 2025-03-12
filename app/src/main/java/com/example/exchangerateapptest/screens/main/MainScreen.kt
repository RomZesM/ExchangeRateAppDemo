package com.example.exchangerateapptest.screens.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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


@Composable
fun MainScreen() {
	val navController: NavHostController = rememberNavController()
	val sharedViewModel: CurrenciesScreenViewModel = hiltViewModel()

	val listOfScreensWithBottomAppBar = listOf(
		Screen.Currencies.route,
		Screen.Favourites.route
	)


	Scaffold(
		topBar = {
			MyTopAppBar(
				navController = navController
			)
		},
		bottomBar = {
			if (currentRoute(navController) in listOfScreensWithBottomAppBar) {
				BottomAppBar(modifier = Modifier) {
					MyBottomTabsBar(
						navController = navController
					)
				}
			}

		},
		content = { padding ->
			MainScreenContent(
				sharedViewModel = sharedViewModel,
				padding = padding,
				navController = navController,
			)
		}
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
