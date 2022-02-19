package com.example.moviesdb.di.component

import android.content.Context
import com.example.moviesdb.MainActivity
import com.example.moviesdb.MyApplication
import com.example.moviesdb.di.module.AppModule
import com.example.moviesdb.di.module.RemoteModule
import com.example.moviesdb.di.module.ViewModelsModule
import com.example.moviesdb.presentation.view.MoviesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RemoteModule::class,
        ViewModelsModule::class
    ]
)
interface ApplicationComponent {

    fun inject(app: MyApplication)

    fun inject(activity: MainActivity)

    fun inject(fragment: MoviesFragment)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance applicationContext: Context): ApplicationComponent

    }
}