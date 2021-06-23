package com.codingpizza.fake

import arrow.core.Either
import com.codingpizza.homepublic.RetrieveNasaCollectionUseCase
import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class FakeRetrieveNasaCollectionUseCase(private val fakeNasaRepository: NasaRepository) : RetrieveNasaCollectionUseCase {

    var customResponse : (() -> Either<NasaError,NasaSearchResult>)? = null

    override suspend fun execute(): Either<NasaError, NasaSearchResult> =
        if (customResponse == null) fakeNasaRepository.retrieveNasaCollection()
        else customResponse?.invoke()!!
}