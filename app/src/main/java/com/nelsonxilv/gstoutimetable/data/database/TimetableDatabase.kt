package com.nelsonxilv.gstoutimetable.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nelsonxilv.gstoutimetable.data.model.GroupDbModel
import com.nelsonxilv.gstoutimetable.data.model.LessonDbModel

@Database(
    entities = [
        LessonDbModel::class,
        GroupLessonCrossRef::class,
        GroupDbModel::class
    ],
    version = 1
)
@TypeConverters(ListConverter::class)
abstract class TimetableDatabase : RoomDatabase() {
    abstract fun lessonDao(): TimetableDao
}