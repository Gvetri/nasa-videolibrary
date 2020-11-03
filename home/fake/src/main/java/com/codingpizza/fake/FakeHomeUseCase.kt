package com.codingpizza.fake

import arrow.core.Either
import com.codingpizza.homepublic.HomeUseCase
import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class FakeHomeUseCase(private val fakeNasaRepository: NasaRepository) : HomeUseCase {

    override suspend fun retrieveNasaCollection(): Either<NasaError, NasaSearchResult> =
        fakeNasaRepository.retrieveNasaCollection()
}