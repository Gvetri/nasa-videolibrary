package com.gvetri.kotlin.videolibrary.repository.android

import arrow.core.Either
import com.gvetri.kotlin.videolibrary.datasource.NasaDataSource
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class NasaRepository(private val nasaDataSource: NasaDataSource) {

    suspend fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult> =
        nasaDataSource.retrieveNasaCollection()
}
