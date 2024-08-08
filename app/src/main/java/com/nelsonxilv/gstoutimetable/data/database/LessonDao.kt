package com.nelsonxilv.gstoutimetable.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nelsonxilv.gstoutimetable.data.model.Group
import com.nelsonxilv.gstoutimetable.data.model.Lesson

@Dao
interface LessonDao {

    @Transaction
    @Query("SELECT * FROM groups WHERE groupName = :groupName")
    suspend fun getLessonsByGroup(groupName: String): GroupWithLessons?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: Lesson)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: Group)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupLessonCrossRef(crossRef: GroupLessonCrossRef)

    @Transaction
    suspend fun insertGroupWithLessons(groupWithLessons: GroupWithLessons) {

        insertGroup(groupWithLessons.group)

        groupWithLessons.lessons.forEach { lesson ->
            insertLesson(lesson)

            val crossRef = GroupLessonCrossRef(groupWithLessons.group.groupName, lesson.lessonId)
            insertGroupLessonCrossRef(crossRef)
        }

    }

}