package com.example.exchangerateapptest.common.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
	entities = [
		FavouriteCurrenciesPairEntity::class
	],
	version = 1
)
abstract class AppDatabase : RoomDatabase() {
	abstract val favouriteCurrenciesPairDAO : FavouriteCurrenciesPairDAO
}