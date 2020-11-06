package com.codingpizza.fake

import com.codingpizza.homepublic.DetailUseCase
import com.codingpizza.homepublic.HomeUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val TEST_FAKE_HOME_USECASE = "TestFakeHomeUsecase"
const val TEST_FAKE_DETAIL_USECASE = "TestFakeDetailUsecase"

val fakeHomeUseCaseModule = module {

    single<HomeUseCase>(named(TEST_FAKE_HOME_USECASE)) {
        FakeHomeUseCase(get(named(TEST_FAKE_NASA_REPOSITORY)))
    }

    single<DetailUseCase>(named(TEST_FAKE_DETAIL_USECASE)) {
        FakeDetailUseCase(get(named(TEST_FAKE_NASA_REPOSITORY)))
    }
}