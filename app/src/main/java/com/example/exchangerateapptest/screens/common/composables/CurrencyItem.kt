package com.example.exchangerateapptest.screens.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exchangerateapptest.R
import com.example.exchangerateapptest.ui.theme.LocalExtendedColors

@Composable
fun CurrencyItem(
	currencyTitle: String,
	currencyValue: String,
	isFavourite: Boolean,
	favToggle: () -> Unit
) {

	val extendedColors = LocalExtendedColors.current

	Box(
		modifier = Modifier
			.fillMaxWidth()
			.background(extendedColors.bgCard, RoundedCornerShape(12.dp))
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
			modifier = Modifier
				.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
		) {
			Text(
				modifier = Modifier
					.weight(1.8f),
				text = currencyTitle,
				style = MaterialTheme.typography.bodySmall
			)

			Text(
				modifier = Modifier
					.weight(1.2f),
				text = currencyValue.toString(),
				textAlign = TextAlign.End,
				style = MaterialTheme.typography.bodyLarge
			)
			Spacer(modifier = Modifier.width(8.dp))

			IconButton(
				onClick = { favToggle() },
				modifier = Modifier.background(Color.Transparent)
			) {
				Icon(
					painter = painterResource(
						if (isFavourite) R.drawable.favorites_on else R.drawable.favorites_off
					),
					contentDescription = "Dropdown Icon",
					tint = if (isFavourite) extendedColors.yellow else extendedColors.secondary,
					modifier = Modifier
						.padding(bottom = 4.dp)
				)
			}
		}
	}
}

@Preview
@Composable
fun CurrencyItemPreview() {
	CurrencyItem(
		currencyTitle = "USD",
		currencyValue = "1.0",
		isFavourite = false,
		favToggle = { }
	)

}