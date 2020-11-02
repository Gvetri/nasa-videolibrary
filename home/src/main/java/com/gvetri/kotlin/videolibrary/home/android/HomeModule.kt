package com.gvetri.kotlin.videolibrary.home.android

import com.codingpizza.di.NASA_REPOSITORY
import com.codingpizza.homepublic.HomeUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val HOME_USECASE = "HomeViewModel"

val homeModule = module {
    single<HomeUseCase>(named(HOME_USECASE)) { HomeUseCaseImpl(nasaRepository = get(named(NASA_REPOSITORY))) }
    viewModel { HomeViewModel(homeUseCase = get(named(HOME_USECASE))) }
}
