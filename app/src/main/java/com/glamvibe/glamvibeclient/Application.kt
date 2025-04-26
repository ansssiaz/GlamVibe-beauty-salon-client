package com.glamvibe.glamvibeclient

import android.app.Application
import com.glamvibe.glamvibeclient.di.apiModule
import com.glamvibe.glamvibeclient.di.repositoryModule
import com.glamvibe.glamvibeclient.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(listOf(repositoryModule, apiModule, viewModelModule))
        }
    }
}