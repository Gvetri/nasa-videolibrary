package com.gvetri.kotlin.videolibrary.home.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.codingpizza.homepublic.RetrieveNasaCollectionUseCase
import com.gvetri.kotlin.videolibrary.core.LoadingState
import com.gvetri.kotlin.videolibrary.core.repository.Event
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val retrieveNasaCollectionUseCase: RetrieveNasaCollectionUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _loadingLiveData: MutableLiveData<Event<LoadingState>> = MutableLiveData()
    val loadingLiveData: LiveData<Event<LoadingState>> = _loadingLiveData
    private val _nasaLiveData: MutableLiveData<NasaSearchResult> = MutableLiveData()
    val nasaLiveData: LiveData<NasaSearchResult> = _nasaLiveData

    private val _errorLiveData: MutableLiveData<Event<Unit>> = MutableLiveData()
    val errorLiveData: LiveData<Event<Unit>> = _errorLiveData

    init {
        retrieveNasaCollection()
    }

    private fun retrieveNasaCollection() {
        viewModelScope.launch(dispatcher) {
            _loadingLiveData.value = Event(LoadingState.Loading)
            when (val result = retrieveNasaCollectionUseCase.execute()) {
                is Either.Right -> {
                    _loadingLiveData.value = Event(LoadingState.NotLoading)
                    _nasaLiveData.value = result.b
                }
                is Either.Left -> {
                    _loadingLiveData.value = Event(LoadingState.NotLoading)
                    _errorLiveData.value = Event(Unit)
                }
            }
        }
    }
}
