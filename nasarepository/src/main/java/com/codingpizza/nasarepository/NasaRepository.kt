package com.codingpizza.nasarepository

import arrow.core.Either
import com.gvetri.kotlin.videolibrary.model.NasaAssetsResult
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

interface NasaRepository {
    suspend fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult>
    suspend fun retrieveVideoUrl(href: String): Either<NasaError, NasaAssetsResult>
}
