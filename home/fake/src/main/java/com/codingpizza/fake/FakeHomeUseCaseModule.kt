package com.codingpizza.fake

import com.codingpizza.homepublic.RetrieveVideoUrlUseCase
import com.codingpizza.homepublic.RetrieveNasaCollectionUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val TEST_FAKE_HOME_USECASE = "TestFakeHomeUsecase"
const val TEST_FAKE_DETAIL_USECASE = "TestFakeDetailUsecase"

val fakeHomeUseCaseModule = module {

    single<RetrieveNasaCollectionUseCase>(named(TEST_FAKE_HOME_USECASE)) {
        FakeRetrieveNasaCollectionUseCase(get(named(TEST_FAKE_NASA_REPOSITORY)))
    }

    single<RetrieveVideoUrlUseCase>(named(TEST_FAKE_DETAIL_USECASE)) {
        FakeRetrieveVideoUrlUseCase(get(named(TEST_FAKE_NASA_REPOSITORY)))
    }
}