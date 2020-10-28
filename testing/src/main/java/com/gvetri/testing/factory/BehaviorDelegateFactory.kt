package com.gvetri.testing.factory

import com.codingpizza.nasaapi.NasaApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

class BehaviorDelegateFactory {

    fun generate(): BehaviorDelegate<NasaApi> {
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder().baseUrl("https://test.com")
            .client(OkHttpClient())
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
        val networkBehavior = NetworkBehavior.create().also { it.setErrorPercent(0) }
        val mockRetrofit = MockRetrofit.Builder(retrofit)
            .networkBehavior(networkBehavior)
            .build()

        return mockRetrofit.create(NasaApi::class.java)
    }
}
