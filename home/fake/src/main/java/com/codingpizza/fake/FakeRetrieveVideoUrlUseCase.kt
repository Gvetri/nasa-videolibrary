package com.codingpizza.fake

import arrow.core.Either
import com.codingpizza.homepublic.RetrieveVideoUrlUseCase
import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.kotlin.videolibrary.model.NasaAssetsResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class FakeRetrieveVideoUrlUseCase(private val fakeNasaRepository: NasaRepository) : RetrieveVideoUrlUseCase {

    var customExecute: (() -> Either<NasaError, String>)? = null

    override suspend fun execute(href: String): Either<NasaError, String> {
        return if (customExecute == null) {
            val assetsResult = fakeNasaRepository.retrieveVideoUrl(href)
            return assetsResult.fold(::ifRight, ::ifLeft)
        } else customExecute?.invoke()!!
    }

    private fun ifLeft(nasaAssetsResult: NasaAssetsResult): Either<NasaError, String> {
        val obtainLinkFromList =
            nasaAssetsResult.hrefLinks.mapNotNull(::filterByExtension)
                .firstOrNull()
        return if (obtainLinkFromList == null) Either.left(
            NasaError(
                400,
                "No videos available for this id"
            )
        )
        else Either.right(obtainLinkFromList)
    }

    private fun filterByExtension(link: String): String? = when {
        link.endsWith(".mp4") -> link
        else -> null
    }

    private fun ifRight(nasaError: NasaError): Either<NasaError, String> = Either.left(nasaError)

}