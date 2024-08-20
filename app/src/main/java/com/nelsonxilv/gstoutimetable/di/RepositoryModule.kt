package com.nelsonxilv.gstoutimetable.di

import com.nelsonxilv.gstoutimetable.data.TimetableRepositoryImpl
import com.nelsonxilv.gstoutimetable.domain.TimetableRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindTimetableRepository(repository: TimetableRepositoryImpl): TimetableRepository
}