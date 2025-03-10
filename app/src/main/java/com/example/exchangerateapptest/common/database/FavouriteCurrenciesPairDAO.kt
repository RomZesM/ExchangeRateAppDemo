package com.example.exchangerateapptest.common.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteCurrenciesPairDAO {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun upsert(favoritePair: FavouriteCurrenciesPairEntity)

	@Query("SELECT * FROM favorite")
	fun observe(): Flow<List<FavouriteCurrenciesPairEntity>>

	@Query("DELETE FROM favorite WHERE id = :id")
	suspend fun delete(id: String)


}