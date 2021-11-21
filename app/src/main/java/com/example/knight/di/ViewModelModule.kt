package com.example.knight.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    @Named(IO_DISPATCHER)
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    @ViewModelScoped
    @Named(CALC_DISPATCHER)
    fun provideCalcDispatcher() = Dispatchers.Default



    companion object {
        const val IO_DISPATCHER = "IO_DISPATCHER"
        const val CALC_DISPATCHER = "CALC_DISPATCHER"
    }
}