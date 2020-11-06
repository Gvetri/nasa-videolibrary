package com.codingpizza.fake

import com.codingpizza.apimodel.NasaSearchApiModel
import com.codingpizza.fakenasaapi.FakeNasaApi
import com.codingpizza.nasaapi.NasaApi
import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

const val TEST_DEFAULT_SEARCH_RESPONSE: String = "TestDefaultNasaSearchResponse"
const val TESTING_NETWORK_NASA_DATASOURCE = "TestingNetworkDataSource"
const val TEST_NASA_API = "TestNasaApi"
const val TEST_FAKE_NASA_API = "TestFakeNasaApi"
const val TEST_NASA_RETROFIT = "TestNasaRetrofit"
const val TEST_NASA_HTTPCLIENT = "TestNasaHttpClient"
const val TEST_HTTP_CLIENT_LOG_INTERCEPTOR = "TestLoggerInterceptor"
const val TEST_BEHAVIOR_DELEGATE = "TestBehaviorDelegate"
const val TEST_MOCK_RETROFIT = "TestMockRetrofit"
const val TEST_NETWORK_BEHAVIOR = "TestNetworkBehavior"
const val TEST_BASE_URL = "https://example.com"

val fakeNetworkNasaApiModule = module {

    single<NasaDataSource>(named(TESTING_NETWORK_NASA_DATASOURCE)) {
        FakeNasaNetworkDataSource(
            fakeNasaApi = get(named(TEST_NASA_API)),
            nasaSearchMapper = ::nasaSearchResultMapper
        )
    }

    single(named(TEST_NASA_RETROFIT)) {
        provideFakeNasaRetrofit(
            client = get(
                named(
                    TEST_NASA_HTTPCLIENT
                )
            )
        )
    }

    single(named(TEST_NASA_HTTPCLIENT)) {
        provideFakeHttpClient(
            interceptor = get(
                named(
                    TEST_HTTP_CLIENT_LOG_INTERCEPTOR
                )
            )
        )
    }

    single(named(TEST_HTTP_CLIENT_LOG_INTERCEPTOR)) { provideFakeInterceptor() }

    single(named(TEST_NASA_API)) {
        provideTestNasaApi(
            behaviorDelegate = get(named(TEST_BEHAVIOR_DELEGATE)),
            defaultResponse = get(named(TEST_DEFAULT_SEARCH_RESPONSE))
        )
    }

    single(named(TEST_FAKE_NASA_API)) {
        provideFakeNasaApi(
            behaviorDelegate = get(named(TEST_BEHAVIOR_DELEGATE)),
            defaultResponse = get(named(TEST_DEFAULT_SEARCH_RESPONSE))
        )
    }

    single(named(TEST_DEFAULT_SEARCH_RESPONSE)) { provideDefaultResponse() }

    single(named(TEST_BEHAVIOR_DELEGATE)) {
        provideBehaviorDelegate(get(named(TEST_MOCK_RETROFIT)))
    }

    single(named(TEST_MOCK_RETROFIT)) {
        provideMockRetrofit(get(named(TEST_NASA_RETROFIT)), get(named(TEST_NETWORK_BEHAVIOR)))
    }

    single(named(TEST_NETWORK_BEHAVIOR)) {
        provideNetworkBehavior()
    }
}

fun provideMockRetrofit(retrofit: Retrofit, networkBehaviour: NetworkBehavior): MockRetrofit {
    return MockRetrofit.Builder(retrofit)
        .networkBehavior(networkBehaviour)
        .build()
}

fun provideNetworkBehavior(): NetworkBehavior {
    return NetworkBehavior.create().apply { setErrorPercent(0) }
}

fun provideBehaviorDelegate(mockRetrofit: MockRetrofit): BehaviorDelegate<NasaApi> =
    mockRetrofit.create(NasaApi::class.java)

fun provideDefaultResponse(): Response<NasaSearchApiModel?> =
    Response.success(FakeNasaSearchFactory.obtainNasaSearchModel())


fun provideTestNasaApi(
    behaviorDelegate: BehaviorDelegate<NasaApi>,
    defaultResponse: Response<NasaSearchApiModel?>
): NasaApi = FakeNasaApi(behaviorDelegate, defaultResponse)

fun provideFakeNasaApi(
    behaviorDelegate: BehaviorDelegate<NasaApi>,
    defaultResponse: Response<NasaSearchApiModel?>
): FakeNasaApi = FakeNasaApi(behaviorDelegate, defaultResponse)

fun provideFakeInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun provideFakeHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient()
    .newBuilder()
    .addInterceptor(interceptor)
    .build()

fun provideFakeNasaRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(TEST_BASE_URL)
    .client(client)
    .addConverterFactory(Json.asConverterFactory(contentType = "application/json".toMediaType()))
    .build()
