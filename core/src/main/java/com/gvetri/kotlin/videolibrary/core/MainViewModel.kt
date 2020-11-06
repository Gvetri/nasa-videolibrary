package com.gvetri.kotlin.videolibrary.core

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _isPlayingLiveData = MutableLiveData<Boolean>()

    val isPlayingLiveData: LiveData<Boolean> = _isPlayingLiveData

    fun isPlaying(): Boolean = isPlayingLiveData.value ?: false


    fun updateIsVideoPlaying(isPlaying: Boolean = false) {
        _isPlayingLiveData.value = isPlaying
    }
}