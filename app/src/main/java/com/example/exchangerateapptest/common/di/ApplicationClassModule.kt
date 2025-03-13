package com.example.exchangerateapptest.common.di

import android.app.Application
import androidx.room.Room
import com.example.exchangerateapptest.common.Constants
import com.example.exchangerateapptest.common.database.AppDatabase
import com.example.exchangerateapptest.common.database.FavouriteCurrenciesPairDAO
import com.example.exchangerateapptest.networking.FreeCurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

	@Provides
	@Singleton
	fun httpClient(): OkHttpClient {
		return OkHttpClient.Builder()
			.build()
	}

	@Provides
	@Singleton
	fun retrofit(httpClient: OkHttpClient): Retrofit {
		return Retrofit.Builder()
			.baseUrl(Constants.BASE_URL)
			.addConverterFactory(MoshiConverterFactory.create())
			.client(httpClient)
			.build()
	}

	@Provides
	fun freeCurrencyApi(retrofit: Retrofit): FreeCurrencyApi {
		return retrofit.create(FreeCurrencyApi::class.java)
	}

	@Provides
	@Singleton
	fun myAppDatabase(application: Application): AppDatabase {
		return Room.databaseBuilder(
			application,
			AppDatabase::class.java,
			Constants.DATABASE_NAME
		).build()
	}

	@Provides
	fun favoriteCurrenciesPairDao(database: AppDatabase): FavouriteCurrenciesPairDAO {
		return database.favouriteCurrenciesPairDAO
	}

}