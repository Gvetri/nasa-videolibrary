package com.gvetri.kotlin.videolibrary.home.android

import com.codingpizza.di.NASA_REPOSITORY
import com.codingpizza.homepublic.RetrieveVideoUrlUseCase
import com.codingpizza.homepublic.RetrieveNasaCollectionUseCase
import com.gvetri.kotlin.videolibrary.home.android.detail.RetrieveVideoUrlUseCaseImpl
import com.gvetri.kotlin.videolibrary.home.android.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val HOME_USECASE = "HomeViewModel"
const val DETAIL_USECASE = "DetailUseCase"

val homeModule = module {
    single<RetrieveNasaCollectionUseCase>(named(HOME_USECASE)) {
        RetrieveNasaCollectionUseCaseImpl(
            nasaRepository = get(
                named(
                    NASA_REPOSITORY
                )
            )
        )
    }

    single<RetrieveVideoUrlUseCase>(named(DETAIL_USECASE)) {
        RetrieveVideoUrlUseCaseImpl(
            nasaRepository = get(
                named(
                    NASA_REPOSITORY
                )
            )
        )
    }

    viewModel { HomeViewModel(retrieveNasaCollectionUseCase = get(named(HOME_USECASE))) }
    viewModel { DetailViewModel(retrieveVideoUrlUseCase = get(named(DETAIL_USECASE))) }
}
