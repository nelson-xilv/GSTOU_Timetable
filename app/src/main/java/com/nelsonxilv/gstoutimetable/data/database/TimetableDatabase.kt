package com.nelsonxilv.gstoutimetable.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nelsonxilv.gstoutimetable.data.model.Group
import com.nelsonxilv.gstoutimetable.data.model.Lesson

@Database(
    entities = [
        Lesson::class,
        GroupLessonCrossRef::class, Group::class
    ],
    version = 1
)
@TypeConverters(ListConverter::class)
abstract class TimetableDatabase : RoomDatabase() {
    abstract fun lessonDao(): TimetableDao
}