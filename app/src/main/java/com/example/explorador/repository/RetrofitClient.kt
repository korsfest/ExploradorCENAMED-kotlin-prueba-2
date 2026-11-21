package com.example.explorador.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:tssQFq_9/"
    private const val EXTERNAL_WEATHER_URL = "https://api.open-meteo.com/v1/"

    val apiService: CenamadApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CenamadApiService::class.java)
    }

    val externalApiService: ExternalApiService by lazy {
        Retrofit.Builder()
            .baseUrl(EXTERNAL_WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExternalApiService::class.java)
    }
}
