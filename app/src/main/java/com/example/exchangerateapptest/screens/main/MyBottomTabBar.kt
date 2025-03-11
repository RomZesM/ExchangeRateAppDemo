package com.example.exchangerateapptest.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MyBottomTabsBar(
	navController: NavHostController
) {
	val bottomTabs = listOf(
		BottomNavItem(Screen.Currencies.route, "Currencies", Icons.Default.Home),
		BottomNavItem(Screen.Favourites.route, "Favourites", Icons.Default.Star)
	)

	NavigationBar {
		val currentRoute = currentRoute(navController)
		bottomTabs.forEachIndexed { _, bottomTab ->
			NavigationBarItem(
				alwaysShowLabel = true,
				icon = { Icon(bottomTab.icon, contentDescription = bottomTab.label) },
				label = { Text(bottomTab.label) },
				selected = currentRoute == bottomTab.route,
				onClick = {
					if (currentRoute != bottomTab.route) {
						navController.navigate(bottomTab.route) {
							popUpTo(navController.graph.startDestinationId) {
								saveState = true
							}
							launchSingleTop = true
							restoreState = true
						}
					}
				},
				colors = NavigationBarItemDefaults.colors(
					indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
				)
			)
		}
	}
}

@Composable
fun currentRoute(navController: NavHostController): String? {
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	return navBackStackEntry?.destination?.route
}

data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)