package com.gvetri.kotlin.videolibrary.core

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val MAIN_VIEW_MODEL = "MainViewModel"
val coreModule = module {
    viewModel { MainViewModel() }
}