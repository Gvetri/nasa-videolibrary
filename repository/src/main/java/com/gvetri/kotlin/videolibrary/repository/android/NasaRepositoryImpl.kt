package com.gvetri.kotlin.videolibrary.repository.android

import arrow.core.Either
import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class NasaRepositoryImpl(private val nasaDataSource: NasaDataSource) : NasaRepository {

    override suspend fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult> =
        nasaDataSource.retrieveNasaCollection()
}
