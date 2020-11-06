package com.gvetri.kotlin.videolibrary.home.android

import arrow.core.Either
import com.codingpizza.homepublic.HomeUseCase
import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class HomeUseCaseImpl(val nasaRepository: NasaRepository) : HomeUseCase {
    override suspend fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult> =
        nasaRepository.retrieveNasaCollection()
}
