package com.gvetri.kotlin.videolibrary.home.android

import androidx.lifecycle.ViewModel
import com.codingpizza.homepublic.HomeUseCase

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {
    fun test() {
        print("Test")
    }
}
