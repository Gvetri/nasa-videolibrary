package com.codingpizza.homepublic

import arrow.core.Either
import com.gvetri.kotlin.videolibrary.model.error.NasaError

interface DetailUseCase {

    suspend fun retrieveVideoUrl(href: String): Either<NasaError, String>

}