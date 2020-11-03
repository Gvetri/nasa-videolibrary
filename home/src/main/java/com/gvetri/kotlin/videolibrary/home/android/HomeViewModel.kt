package com.gvetri.kotlin.videolibrary.home.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.codingpizza.homepublic.HomeUseCase
import com.gvetri.kotlin.videolibrary.core.repository.Event
import com.gvetri.kotlin.videolibrary.model.NasaSearchResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeUseCase: HomeUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _nasaLiveData: MutableLiveData<NasaSearchResult> = MutableLiveData()
    val nasaLiveData: LiveData<NasaSearchResult> = _nasaLiveData

    private val _errorLiveData: MutableLiveData<Event<Unit>> = MutableLiveData()
    val errorLiveData: LiveData<Event<Unit>> = _errorLiveData

    init {
        retrieveNasaCollection()
    }

    fun retrieveNasaCollection() {
        viewModelScope.launch(dispatcher) {
            when (val result = homeUseCase.retrieveNasaCollection()) {
                is Either.Right -> _nasaLiveData.postValue(result.b)
                is Either.Left -> _errorLiveData.postValue(Event(Unit))
            }
        }
    }

}
