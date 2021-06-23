package com.codingpizza.homepublic

import arrow.core.Either
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

interface RetrieveNasaCollectionUseCase {
    suspend fun execute(): Either<NasaError, NasaSearchResult>
}
