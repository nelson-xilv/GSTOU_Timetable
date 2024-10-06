package com.nelsonxilv.gstoutimetable.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineExceptionHandlerModule {

    private const val TAG = "CoroutineExceptionHandler"

    @Provides
    @Singleton
    @DefaultCoroutineExceptionHandler
    fun provideCoroutineExceptionHandler(): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "Error: $throwable")
        }

}