package com.nelsonxilv.gstoutimetable.data

import com.nelsonxilv.gstoutimetable.data.database.GroupWithLessons
import com.nelsonxilv.gstoutimetable.data.database.LessonDao
import com.nelsonxilv.gstoutimetable.data.mapper.LessonMapper
import com.nelsonxilv.gstoutimetable.data.model.Group
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.data.network.TimetableApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TimetableRepository @Inject constructor(
    private val apiService: TimetableApiService,
    private val lessonDao: LessonDao,
    private val lessonMapper: LessonMapper
) {

    suspend fun getLessons(groupName: String): Flow<List<Lesson>> {
        val localData = lessonDao.getLessonsByGroup(groupName)

        return flow {
            if (localData == null) {
                val lessons = getScheduleFromApi(groupName)
                cacheLessons(groupName, lessons)
                emit(lessons)
            } else {
                val lessons = localData.lessons
                emit(lessons)
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun getScheduleFromApi(groupName: String): List<Lesson> {
        val lessonDtoList = apiService.getSchedule(groupName)
        val lessonList = lessonDtoList.map { dto -> lessonMapper.mapDtoToModel(dto) }

        return lessonList
    }

    private suspend fun cacheLessons(groupName: String, lessons: List<Lesson>) {
        val group = Group(groupName)
        val groupWithLessons = GroupWithLessons(group, lessons)

        lessonDao.insertGroupWithLessons(groupWithLessons)
    }

}