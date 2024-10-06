package com.nelsonxilv.gstoutimetable.domain

import com.nelsonxilv.gstoutimetable.domain.entity.Group
import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import kotlinx.coroutines.flow.Flow

interface TimetableRepository {

    suspend fun getLessonList(groupName: String): Flow<List<Lesson>>

    suspend fun deleteGroupAndLessons(groupName: String)

    fun getGroupList(): Flow<List<Group>>
}