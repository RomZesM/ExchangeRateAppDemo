package com.example.exchangerateapptest.screens.favouritescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerateapptest.currencies.FavouriteCurrenciesPair
import com.example.exchangerateapptest.currencies.usecases.FetchFavouritesCurrenciesPairsUseCase
import com.example.exchangerateapptest.currencies.usecases.ToggleFavouritePairIconUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteScreenViewModel @Inject constructor(
	private val fetchFavouritesCurrenciesPairsUseCase: FetchFavouritesCurrenciesPairsUseCase,
	private val toggleFavouritePairIconUseCase: ToggleFavouritePairIconUseCase
) : ViewModel() {

	private val _favourites = MutableStateFlow<List<FavouriteCurrenciesPair>>(emptyList())
	val favourites = _favourites.asStateFlow()

	fun fetchFavouriteCurrenciesPairs() {
		viewModelScope.launch(Dispatchers.IO) {
			_favourites.value = fetchFavouritesCurrenciesPairsUseCase.invoke()
		}
	}
}