package com.gvetri.kotlin.videolibrary.app

import android.app.Application
//import com.codingpizza.di.mainModule
import com.codingpizza.di.repositoryModule
import com.gvetri.kotlin.videolibrary.home.android.homeModule
import com.gvetri.kotlin.videolibrary.network.module.networkModule
import com.gvetri.kotlin.videolibrary.network.networkDataSourceModule
//import com.gvetri.kotlin.videolibrary.repository.android.repositoryModule
import org.koin.core.context.startKoin

class NasaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                homeModule,
                repositoryModule,
                networkDataSourceModule,
                networkModule
            )
        }
    }
}
