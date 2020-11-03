package com.gvetri.kotlin.videolibrary.network.module

import com.codingpizza.nasaapi.NasaApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

const val BASE_URL = "https://images-api.nasa.gov"
internal const val NASA_API_SERVICE = "NasaApi"
internal const val NASA_RETROFIT = "NasaRetrofit"
internal const val NASA_HTTPCLIENT = "NasaHttpClient"
internal const val HTTP_CLIENT_LOG_INTERCEPTOR = "LoggerInterceptor"

val networkModule = module {
    single(named(HTTP_CLIENT_LOG_INTERCEPTOR)) { provideInterceptor() }
    single(named(NASA_HTTPCLIENT)) {
        provideHttpclient(
            interceptor = get(
                named(
                    HTTP_CLIENT_LOG_INTERCEPTOR
                )
            )
        )
    }
    single(named(NASA_RETROFIT)) { provideRetrofit(client = get(named(NASA_HTTPCLIENT))) }
    single(named(NASA_API_SERVICE)) { provideNasaApiService(retrofit = get(named(NASA_RETROFIT))) }
}

private fun provideNasaApiService(retrofit: Retrofit): NasaApi =
    retrofit.create(NasaApi::class.java)

private fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(contentType = "application/json".toMediaType()))
        .build()
}

private fun provideHttpclient(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient()
    .newBuilder()
    .addInterceptor(interceptor)
    .build()

private fun provideInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
