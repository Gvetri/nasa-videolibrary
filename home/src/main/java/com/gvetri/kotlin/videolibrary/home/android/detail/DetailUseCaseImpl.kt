package com.gvetri.kotlin.videolibrary.home.android.detail

import arrow.core.Either
import com.codingpizza.homepublic.DetailUseCase
import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.kotlin.videolibrary.model.NasaAssetsResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class DetailUseCaseImpl(private val nasaRepository: NasaRepository) : DetailUseCase {

    override suspend fun retrieveVideoUrl(href: String): Either<NasaError, String> {
        val assetsResult = nasaRepository.retrieveVideoUrl(href)
        return assetsResult.fold(::ifRight, ::ifLeft)
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