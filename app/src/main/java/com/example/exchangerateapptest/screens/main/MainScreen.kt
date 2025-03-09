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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exchangerateapptest.screens.currencyscreen.CurrenciesScreen
import com.example.exchangerateapptest.screens.favouritescreen.FavouritesScreen
import com.example.exchangerateapptest.screens.filtersscreen.FiltersScreen

@Composable
fun MainScreen() {
	val navController: NavHostController = rememberNavController()

	Scaffold(
		topBar = {
			MyTopAppBar(
				navController = navController
			)
		},
		bottomBar = {
			BottomAppBar(modifier = Modifier) {
				MyBottomTabsBar(
					navController = navController
				)
			}
		},
		content = { padding ->
			MainScreenContent(
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
) {
	Surface(
		modifier = Modifier
			.padding(padding)
			.padding(horizontal = 12.dp),
	) {
		NavHost(
			modifier = Modifier.fillMaxSize(),
			navController = navController,
			enterTransition = { fadeIn(animationSpec = tween(200)) },
			exitTransition = { fadeOut(animationSpec = tween(200)) },
			startDestination = "Currencies",
		) {
			composable("Currencies") {
				CurrenciesScreen(
					navController = navController
				)
			}
			composable("Favourites") {
				FavouritesScreen()
			}
			composable("Filters") {
				FiltersScreen()
			}
		}
	}
}
