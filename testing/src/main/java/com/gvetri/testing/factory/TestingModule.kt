package com.gvetri.testing.factory

import com.codingpizza.nasaapi.NasaApi
import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


const val TESTING_NETWORK_NASA_DATASOURCE = "TestingNetworkDataSource"
const val TEST_NASA_RETROFIT = "TestNasaRetrofit"
const val TEST_NASA_HTTPCLIENT = "TestNasaHttpClient"
const val TEST_HTTP_CLIENT_LOG_INTERCEPTOR = "TestLoggerInterceptor"
const val TEST_BASE_URL = "https://example.com"


val testModule = module {
    single<NasaDataSource>(named(TESTING_NETWORK_NASA_DATASOURCE)) { FakeNasaDataSource() }

    single(named(TEST_NASA_RETROFIT)) { provideFakeNasaRetrofit(get(named(TEST_NASA_HTTPCLIENT))) }

    single(named(TEST_NASA_HTTPCLIENT)) {
        provideFakeHttpClient(
            get(
                named(
                    TEST_HTTP_CLIENT_LOG_INTERCEPTOR
                )
            )
        )
    }

    single(named(TEST_HTTP_CLIENT_LOG_INTERCEPTOR)) { provideFakeInterceptor() }
}

fun provideFakeNasaApi(retrofit: Retrofit): NasaApi = retrofit.create(NasaApi::class.java)

fun provideFakeInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun provideFakeHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient()
    .newBuilder()
    .addInterceptor(interceptor)
    .build()

fun provideFakeNasaRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(TEST_BASE_URL)
        .client(client)
        .addConverterFactory(Json.asConverterFactory(contentType = "application/json".toMediaType()))
        .build()
}
