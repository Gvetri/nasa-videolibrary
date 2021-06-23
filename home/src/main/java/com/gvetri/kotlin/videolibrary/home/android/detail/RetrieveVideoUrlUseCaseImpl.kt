package com.gvetri.kotlin.videolibrary.home.android.detail

import arrow.core.Either
import com.codingpizza.homepublic.RetrieveVideoUrlUseCase
import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.kotlin.videolibrary.model.NasaAssetsResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class RetrieveVideoUrlUseCaseImpl(private val nasaRepository: NasaRepository) : RetrieveVideoUrlUseCase {

    private val noHrefFoundErrorCode = 400

    private val noLinkFoundErrorMessage = "No videos available for this id"

    override suspend fun execute(href: String): Either<NasaError, String> {
        val assetsResult = nasaRepository.retrieveVideoUrl(href)
        return assetsResult.fold(::ifRight, ::ifLeft)
    }

    private fun ifLeft(nasaAssetsResult: NasaAssetsResult): Either<NasaError, String> {
        val obtainLinkFromList =
            nasaAssetsResult.hrefLinks.mapNotNull(::filterByExtension)
                .firstOrNull()
                ?.replace("http", "https")
        return if (obtainLinkFromList == null) Either.left(
            NasaError(
                noHrefFoundErrorCode,
                noLinkFoundErrorMessage
            )
        )
        else Either.right(obtainLinkFromList)
    }

    private fun ifRight(nasaError: NasaError): Either<NasaError, String> = Either.left(nasaError)

    private fun filterByExtension(link: String): String? = when {
        link.endsWith(".mp4") -> link
        else -> null
    }

}