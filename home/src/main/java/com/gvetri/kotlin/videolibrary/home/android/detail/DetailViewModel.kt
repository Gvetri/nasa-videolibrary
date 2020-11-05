package com.gvetri.kotlin.videolibrary.home.android.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingpizza.homepublic.DetailUseCase
import com.gvetri.kotlin.videolibrary.core.repository.Event
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import com.gvetri.kotlin.videolibrary.model.error.NasaError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val detailUseCase: DetailUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) :
    ViewModel() {

    private val _videoUrlLiveData: MutableLiveData<String> = MutableLiveData()
    val videoUrlLiveData: LiveData<String> = _videoUrlLiveData

    private val _errorLiveData: MutableLiveData<Event<NasaError>> = MutableLiveData()
    val errorLiveData: LiveData<Event<NasaError>> = _errorLiveData

    private val noHrefErrorCode = 400
    private val noHrefErrorMessage = "Href can't be null"

    fun retrieveVideoUrl(href: String?) {
        if (href == null) {
            _errorLiveData.value =
                Event(NasaError(noHrefErrorCode, noHrefErrorMessage))
        } else {
            viewModelScope.launch(dispatcher) {
                val response = detailUseCase.retrieveVideoUrl(href)
                response.fold(::onCallFailed, ::onCallSuccessful)
            }
        }
    }

    private fun onCallSuccessful(videoUrl: String) {
        _videoUrlLiveData.value = videoUrl
    }

    private fun onCallFailed(nasaError: NasaError) {
        _errorLiveData.value = Event(nasaError)
    }

}
