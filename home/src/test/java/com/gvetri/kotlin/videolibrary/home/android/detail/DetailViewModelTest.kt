package com.gvetri.kotlin.videolibrary.home.android.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.codingpizza.fake.FakeDetailUseCase
import com.codingpizza.fake.TEST_FAKE_DETAIL_USECASE
import com.codingpizza.fake.TEST_FAKE_NASA_REPOSITORY
import com.codingpizza.fake.fakeHomeUseCaseModule
import com.codingpizza.fake.fakeNasaRepositoryModule
import com.codingpizza.fake.fakeNetworkNasaApiModule
import com.codingpizza.homepublic.DetailUseCase
import com.google.common.truth.Truth.assertThat
import com.gvetri.kotlin.videolibrary.core.repository.getOrAwaitValue
import com.gvetri.kotlin.videolibrary.model.error.NasaError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

@ExperimentalCoroutinesApi
class DetailViewModelTest : KoinTest {

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

    private val testDispatcher = TestCoroutineDispatcher()

    private val detailUseCase: DetailUseCase by inject(named(TEST_FAKE_DETAIL_USECASE))


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
    fun `ViewModel should update errorLiveData when there is no video id available`() =
        runBlocking {
            //given
            val viewModel = DetailViewModel(detailUseCase, testDispatcher)
            //when
            viewModel.retrieveVideoUrl(null)
            //then
            val expected = NasaError(400, "Href can't be null")

            assertThat(viewModel.errorLiveData.getOrAwaitValue().peekContent()).isEqualTo(expected)
        }

    @Test
    fun `ViewModel should update videoUrl livedata when the result is successful`() =
        runBlocking {
            //given
            val viewModel = DetailViewModel(detailUseCase, testDispatcher)
            //when
            val validHref = "validId"
            viewModel.retrieveVideoUrl(validHref)

            //then
            val expected =
                "http://images-assets.nasa.gov/video/A Quick Look at Supernova 1987A/A Quick Look at Supernova 1987A~mobile.mp4"

            assertThat(viewModel.videoUrlLiveData.getOrAwaitValue()).isEqualTo(expected)
        }

    @Test
    fun `ViewModel should update errorLiveData when there was an error in the execution`() = runBlocking {

        //given
        val detailUseCase = FakeDetailUseCase(fakeNasaRepository = get(named(TEST_FAKE_NASA_REPOSITORY)))

        detailUseCase.customExecute = {
            Either.left(NasaError(500, "Server error"))
        }
        val viewModel = DetailViewModel(detailUseCase)

        //when
        val validHref = "validId"
        viewModel.retrieveVideoUrl(validHref)

        //then
        val expected = NasaError(500, "Server error")

        assertThat(viewModel.errorLiveData.getOrAwaitValue().peekContent()).isEqualTo(expected)

    }




}