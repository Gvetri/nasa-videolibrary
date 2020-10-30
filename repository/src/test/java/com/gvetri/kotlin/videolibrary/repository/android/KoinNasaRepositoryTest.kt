package com.gvetri.kotlin.videolibrary.repository.android

import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import com.gvetri.testing.factory.NasaSearchFactory
import com.gvetri.testing.factory.TESTING_NETWORK_NASA_DATASOURCE
import com.gvetri.testing.factory.testModule
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class KoinNasaRepositoryTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            testModule
        )
    }

    val networkDataSource: NasaDataSource by inject(named(TESTING_NETWORK_NASA_DATASOURCE))

    @Test
    fun `Repository should call the datasource and return the value`() {
        // given
        val nasaRepository = NasaRepository(networkDataSource)

        // when
        runBlocking {
            val actual = nasaRepository.retrieveNasaCollection()

            // then
            val expectedValue = NasaSearchFactory.generateNasaSearchResult()
            val expected = Either.right(expectedValue)

            assertThat(actual).isEqualTo(expected)
        }
    }
}
