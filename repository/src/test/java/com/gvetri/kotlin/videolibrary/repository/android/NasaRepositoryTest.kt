package com.gvetri.kotlin.videolibrary.repository.android

import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import com.gvetri.testing.factory.FakeNasaDataSource
import com.gvetri.testing.factory.NasaSearchFactory
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NasaRepositoryTest {

    @Test
    fun `Repository should call the datasource and return the value`() {
        // given
        val nasaDataSource: NasaDataSource = FakeNasaDataSource()
        val nasaRepository = NasaRepository(nasaDataSource)
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
