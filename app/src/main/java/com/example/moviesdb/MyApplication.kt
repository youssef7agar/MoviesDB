package com.example.moviesdb

import android.app.Application
import com.example.moviesdb.di.component.ApplicationComponent
import com.example.moviesdb.di.component.DaggerApplicationComponent

class MyApplication : Application() {

    private val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        provideAppComponent().inject(this)
    }

    fun provideAppComponent() = appComponent
}