package com.gvetri.kotlin.videolibrary.repository.android

import arrow.core.Either
import com.codingpizza.fake.NasaSearchFactory
import com.codingpizza.fake.TESTING_NASASEARCH_FACTORY
import com.codingpizza.fake.TESTING_NETWORK_NASA_DATASOURCE
import com.codingpizza.fake.fakeNetworkNasaApiModule
import com.google.common.truth.Truth.assertThat
import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class NasaRepositoryTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            fakeNetworkNasaApiModule
        )
    }

    private val networkDataSource: NasaDataSource by inject(named(TESTING_NETWORK_NASA_DATASOURCE))
    private val nasaSearchFactory: NasaSearchFactory by inject(named(TESTING_NASASEARCH_FACTORY))

    @Test
    fun `Repository should call the datasource and return the value`() {
        // given
        val nasaRepository = NasaRepositoryImpl(networkDataSource)

        // when
        runBlocking {
            val actual = nasaRepository.retrieveNasaCollection()

            // then
            val expectedValue = nasaSearchFactory.generateNasaSearchResult()
            val expected = Either.right(expectedValue)

            assertThat(actual).isEqualTo(expected)
        }
    }
}
