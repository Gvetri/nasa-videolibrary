package com.gvetri.kotlin.videolibrary.home.android

import androidx.lifecycle.ViewModel

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {
    fun test() {
        print("Test")
    }
}
