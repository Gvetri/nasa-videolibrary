package com.gvetri.kotlin.videolibrary.home.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.codingpizza.fake.FakeHomeUseCase
import com.codingpizza.fake.TEST_FAKE_HOME_USECASE
import com.codingpizza.fake.TEST_FAKE_NASA_REPOSITORY
import com.codingpizza.fake.fakeHomeUseCaseModule
import com.codingpizza.fake.fakeNasaRepositoryModule
import com.codingpizza.fake.fakeNetworkNasaApiModule
import com.codingpizza.homepublic.HomeUseCase
import com.google.common.truth.Truth.assertThat
import com.gvetri.kotlin.videolibrary.core.LoadingState
import com.gvetri.kotlin.videolibrary.core.repository.getOrAwaitValue
import com.gvetri.kotlin.videolibrary.model.error.NasaError
import com.gvetri.testing.factory.generateNasaSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.inject

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val nasaKoinTestModule = KoinTestRule.create {
        modules(
            fakeHomeUseCaseModule,
            fakeNasaRepositoryModule,
            fakeNetworkNasaApiModule,
        )
    }

    private val fakeUseCase: HomeUseCase by inject(named(TEST_FAKE_HOME_USECASE))
    private val nasaSearchResult = generateNasaSearchResult()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `ViewModel should call the UseCase and update the success liveData when the result is successful`() =
        runBlocking {
            // given
            val viewModel = HomeViewModel(fakeUseCase, testDispatcher)

            // when

            // then
            val expected = nasaSearchResult
            assertThat(viewModel.nasaLiveData.getOrAwaitValue()).isEqualTo(expected)
        }

    @Test
    fun `ViewModel Should update loading livedata when the data is loading`() = runBlocking {
        // given
        val viewModel = HomeViewModel(fakeUseCase, testDispatcher)

        // when

        // then
        assertThat(viewModel.loadingLiveData.getOrAwaitValue().peekContent()).isInstanceOf(
            LoadingState.Loading::class.java
        )
    }

    @Test
    fun `ViewModel should call the usecase and update the errorLiveData when the result is an error`() =
        runBlocking {
            // given
            val errorMessage = "Server Error"
            val errorCode = 500
            val nasaError = NasaError(errorCode, errorMessage)
            val customExecute = { Either.left(nasaError) }
            val fakeUseCase =
                FakeHomeUseCase(fakeNasaRepository = get(named(TEST_FAKE_NASA_REPOSITORY)))
            fakeUseCase.customExecute = customExecute

            val viewModel = HomeViewModel(fakeUseCase, testDispatcher)

            // when

            // then

            assertThat(
                viewModel.errorLiveData.getOrAwaitValue().peekContent()
            ).isInstanceOf(Unit::class.java)
        }
}
