package com.nelsonxilv.gstoutimetable.di

import com.nelsonxilv.gstoutimetable.data.DateProviderImpl
import com.nelsonxilv.gstoutimetable.domain.DateProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DateModule {
    @Binds
    fun bindDateProvider(dateProvider: DateProviderImpl): DateProvider
}