package com.gvetri.kotlin.videolibrary.home.android

import arrow.core.Either
import com.codingpizza.fake.TEST_FAKE_NASA_REPOSITORY
import com.codingpizza.fake.fakeNasaRepositoryModule
import com.codingpizza.fake.fakeNetworkNasaApiModule
import com.codingpizza.nasarepository.NasaRepository
import com.google.common.truth.Truth.assertThat
import com.gvetri.testing.factory.generateNasaSearchResult
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class RetrieveNasaCollectionUseCaseTest : KoinTest {

    @get:Rule
    val nasaKoinTestRule = KoinTestRule.create {
        modules(
            fakeNasaRepositoryModule,
            fakeNetworkNasaApiModule,
        )
    }

    private val fakeNasaRepository: NasaRepository by inject(named(TEST_FAKE_NASA_REPOSITORY))
    private val nasaSearchResult = generateNasaSearchResult()

    @Test
    fun `Usecase should call the repository and return a value`() {
        runBlocking {
            // given
            val homeUseCase = RetrieveNasaCollectionUseCaseImpl(fakeNasaRepository)

            // when
            val actual = homeUseCase.execute()

            // then
            val expectedValue = nasaSearchResult
            val expected = Either.right(expectedValue)

            assertThat(actual).isEqualTo(expected)
        }
    }
}
