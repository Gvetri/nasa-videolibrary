package com.codingpizza.homepublic

import arrow.core.Either
import com.gvetri.kotlin.videolibrary.model.error.NasaError

interface RetrieveVideoUrlUseCase {

    suspend fun execute(href: String): Either<NasaError, String>

}