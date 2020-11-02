package com.codingpizza.homepublic

import arrow.core.Either
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

interface HomeUseCase {
    suspend fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult>
}
