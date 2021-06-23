package com.gvetri.kotlin.videolibrary.home.android

import arrow.core.Either
import com.codingpizza.homepublic.RetrieveNasaCollectionUseCase
import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class RetrieveNasaCollectionUseCaseImpl(val nasaRepository: NasaRepository) : RetrieveNasaCollectionUseCase {
    override suspend fun execute(): Either<NasaError, NasaSearchResult> =
        nasaRepository.retrieveNasaCollection()
}
