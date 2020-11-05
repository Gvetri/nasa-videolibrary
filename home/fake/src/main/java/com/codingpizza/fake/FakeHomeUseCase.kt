package com.codingpizza.fake

import arrow.core.Either
import com.codingpizza.homepublic.HomeUseCase
import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class FakeHomeUseCase(private val fakeNasaRepository: NasaRepository) : HomeUseCase {

    var customResponse : (() -> Either<NasaError,NasaSearchResult>)? = null

    override suspend fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult> =
        if (customResponse == null) fakeNasaRepository.retrieveNasaCollection()
        else customResponse?.invoke()!!
}