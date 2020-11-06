package com.gvetri.kotlin.videolibrary.home.android

import com.codingpizza.di.NASA_REPOSITORY
import com.codingpizza.homepublic.DetailUseCase
import com.codingpizza.homepublic.HomeUseCase
import com.gvetri.kotlin.videolibrary.home.android.detail.DetailUseCaseImpl
import com.gvetri.kotlin.videolibrary.home.android.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val HOME_USECASE = "HomeViewModel"
const val DETAIL_USECASE = "DetailUseCase"

val homeModule = module {
    single<HomeUseCase>(named(HOME_USECASE)) {
        HomeUseCaseImpl(
            nasaRepository = get(
                named(
                    NASA_REPOSITORY
                )
            )
        )
    }

    single<DetailUseCase>(named(DETAIL_USECASE)) {
        DetailUseCaseImpl(
            nasaRepository = get(
                named(
                    NASA_REPOSITORY
                )
            )
        )
    }

    viewModel { HomeViewModel(homeUseCase = get(named(HOME_USECASE))) }
    viewModel { DetailViewModel(detailUseCase = get(named(DETAIL_USECASE))) }
}
