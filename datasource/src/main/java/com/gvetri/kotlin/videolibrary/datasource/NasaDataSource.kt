package com.gvetri.kotlin.videolibrary.datasource

import arrow.core.Either
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

interface NasaDataSource {
    suspend fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult>
}
