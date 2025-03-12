package com.example.exchangerateapptest.screens.main

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.exchangerateapptest.R
import com.example.exchangerateapptest.ui.theme.LocalExtendedColors

data class BottomNavItem(
	val route: String,
	val label: String,
	val icon: Int,
	val contentDescription: Int
)

@Composable
fun MyBottomTabsBar(
	navController: NavHostController,
) {

	val currentRoute = currentRoute(navController)

	val bottomTabs = listOf(
		BottomNavItem(
			Screen.Currencies.route,
			stringResource(R.string.currency_tab_name),
			R.drawable.wallet_icon,
			R.string.currency_tab_icon_content_description
		),
		BottomNavItem(
			Screen.Favourites.route,
			stringResource(R.string.favourite_tab_name),
			R.drawable.favorites_tab_icon,
			R.string.favourite_tab_icon_content_description
		)
	)

	MyBottomTabLayout(
		currentRoute = currentRoute,
		bottomTabs = bottomTabs
	) { route ->
		navController.navigate(route) {
			popUpTo(navController.graph.startDestinationId) {
				saveState = true
			}
			launchSingleTop = true
			restoreState = true
		}
	}

}

@Composable
fun MyBottomTabLayout(
	currentRoute: String?,
	bottomTabs: List<BottomNavItem>,
	onTabClick: (String) -> Unit,

	) {
	val extendedColors = LocalExtendedColors.current


	NavigationBar(
		containerColor = extendedColors.bgDefault,

		) {
		bottomTabs.forEachIndexed { _, bottomTab ->
			val isSelected = currentRoute == bottomTab.route
			NavigationBarItem(
				alwaysShowLabel = true,
				icon = {
					Icon(
						painter = painterResource(
							bottomTab.icon
						),
						contentDescription = stringResource(bottomTab.contentDescription),
						tint = if (isSelected) extendedColors.primary else extendedColors.secondary,
						modifier = Modifier.height(42.dp),
					)
				},
				label = {
					Text(
						bottomTab.label,
						style = MaterialTheme.typography.labelSmall,
						color = if (isSelected) extendedColors.primary else extendedColors.secondary
					)
				},
				selected = isSelected,
				onClick = {
					if (currentRoute != bottomTab.route) {
						onTabClick(bottomTab.route)
					}
				},
				colors = NavigationBarItemDefaults.colors(
					indicatorColor = extendedColors.secondary
				),
				modifier = Modifier.size(32.dp)

			)
		}
	}
}


@Preview
@Composable
fun MyBottomTabsBarPreview() {
	MyBottomTabLayout(
		currentRoute = "Currencies",
		bottomTabs = listOf(
			BottomNavItem(
				Screen.Currencies.route,
				stringResource(R.string.currency_tab_name),
				R.drawable.wallet_icon,
				R.string.currency_tab_icon_content_description
			),
			BottomNavItem(
				Screen.Favourites.route,
				stringResource(R.string.favourite_tab_name),
				R.drawable.favorites_tab_icon,
				R.string.favourite_tab_icon_content_description
			)
		)

	) { }
}


