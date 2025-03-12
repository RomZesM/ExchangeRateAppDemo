package com.example.exchangerateapptest.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.exchangerateapptest.R
import com.example.exchangerateapptest.ui.theme.LocalExtendedColors


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


	MyTopAppBarLayout(
		currentRoute = currentRoute,
		currentRouteTitle = currentRouteTitle,
		onBackIconCLick = { navController.popBackStack() },
		listOfScreensWithBackArrow = listOfScreensWithBackArrow
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBarLayout(
	currentRoute: String,
	currentRouteTitle: String,
	onBackIconCLick: () -> Unit,
	listOfScreensWithBackArrow: List<String>
) {

	val extendedColors = LocalExtendedColors.current

	TopAppBar(
		title = {
			Row(
				modifier = Modifier
					.fillMaxWidth(),

				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.Start
			) {
				if (currentRoute in listOfScreensWithBackArrow) {
					IconButton(
						onClick = { onBackIconCLick() },
						content = {
							Icon(
								painter = painterResource(
									R.drawable.arrow_back_icon
								),
								contentDescription = "Dropdown Icon",
								tint = extendedColors.primary
							)
						}
					)
				}

				Text(
					text = currentRouteTitle,
					color = extendedColors.textDefault,
					style = MaterialTheme.typography.headlineLarge,
					modifier = Modifier.padding(start = 4.dp)

				)
			}
		},
		colors = TopAppBarDefaults.topAppBarColors(extendedColors.bgHeader),
	)

}

@Preview
@Composable
fun MyTopAppBarPreview() {
	MyTopAppBarLayout(
		currentRoute = Screen.Filters.route,
		currentRouteTitle = Screen.Filters.route,
		onBackIconCLick = {},
		listOfScreensWithBackArrow = listOf(
			Screen.Filters.route
		)
	)
}
