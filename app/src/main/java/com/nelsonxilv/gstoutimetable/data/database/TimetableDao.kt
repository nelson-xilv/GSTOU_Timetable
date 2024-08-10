package com.nelsonxilv.gstoutimetable.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nelsonxilv.gstoutimetable.data.model.Group
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import kotlinx.coroutines.flow.Flow

@Dao
interface TimetableDao {

    @Query("SELECT * FROM groups")
    fun getAllGroups(): Flow<List<Group>>

    @Transaction
    @Query("SELECT * FROM groups WHERE groupName = :groupName")
    suspend fun getGroupWithLessons(groupName: String): GroupWithLessons?

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

    @Query("SELECT * FROM lesson_group_cross_ref WHERE lessonId = :lessonId")
    suspend fun getGroupsForLesson(lessonId: Int): List<Group>

    @Delete
    suspend fun deleteLessons(lessonIds: List<Lesson>)

    @Query("DELETE FROM groups WHERE groupName = :groupName")
    suspend fun deleteGroup(groupName: String)

    @Query("DELETE FROM lesson_group_cross_ref WHERE groupName = :groupName")
    suspend fun deleteGroupLessonCrossRef(groupName: String)

    @Transaction
    suspend fun deleteGroupWithLessons(groupName: String) {
        val groupWithLessons = getGroupWithLessons(groupName)
        val lessonsToDelete = groupWithLessons!!.lessons.filter { lesson ->
            val otherGroups = getGroupsForLesson(lesson.lessonId)
            otherGroups.size == 1 && otherGroups[0].groupName == groupName
        }

        deleteLessons(lessonsToDelete)
        deleteGroupLessonCrossRef(groupName)
        deleteGroup(groupName)
    }

}