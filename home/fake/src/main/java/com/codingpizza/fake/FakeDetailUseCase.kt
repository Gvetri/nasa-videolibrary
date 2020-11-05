package com.codingpizza.fake

import arrow.core.Either
import arrow.core.extensions.andthen.functor.mapConst
import arrow.core.extensions.list.functor.mapConst
import com.codingpizza.homepublic.DetailUseCase
import com.codingpizza.nasarepository.NasaRepository
import com.gvetri.kotlin.videolibrary.model.NasaAssetsResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError

class FakeDetailUseCase(private val fakeNasaRepository: NasaRepository) : DetailUseCase {

    var customExecute: (() -> Either<NasaError, String>)? = null

    override suspend fun retrieveVideoUrl(href: String): Either<NasaError, String> {
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