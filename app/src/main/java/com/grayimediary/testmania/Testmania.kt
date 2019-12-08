package com.grayimediary.testmania

import android.app.Application
import com.grayimediary.testmania.di.apiModule
import com.grayimediary.testmania.di.repositoryModule
import com.grayimediary.testmania.di.viewModelModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Testmania : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()

            modules(listOf(apiModule, repositoryModule, viewModelModule))
        }
    }
}