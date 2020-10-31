package com.codingpizza.fake

import arrow.core.Either
import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class FakeNasaRepository(private val datasource: NasaDataSource) : NasaRepository {
    override suspend fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult> =
        datasource.retrieveNasaCollection()
}
