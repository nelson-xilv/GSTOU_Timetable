package com.nelsonxilv.gstoutimetable.di

import android.content.Context
import androidx.room.Room
import com.nelsonxilv.gstoutimetable.data.database.TimetableDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "database-lesson"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext application: Context): TimetableDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            TimetableDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideLessonDao(database: TimetableDatabase) = database.lessonDao()

}