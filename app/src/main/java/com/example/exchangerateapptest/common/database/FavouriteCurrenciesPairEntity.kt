package com.example.exchangerateapptest.common.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavouriteCurrenciesPairEntity(
	@ColumnInfo(name = "id") @PrimaryKey val id: String,
	@ColumnInfo(name = "base_currency") val baseCurrency: String,
	@ColumnInfo(name = "target_currency") val targetCurrency: String,
	@ColumnInfo(name = "value") val value: String,
	@ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis()
)

