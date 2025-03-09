package com.example.exchangerateapptest.common.di

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


}