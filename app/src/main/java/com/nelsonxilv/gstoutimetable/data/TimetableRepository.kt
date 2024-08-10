package com.nelsonxilv.gstoutimetable.data

import com.nelsonxilv.gstoutimetable.data.database.GroupWithLessons
import com.nelsonxilv.gstoutimetable.data.database.TimetableDao
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
    private val timetableDao: TimetableDao,
    private val lessonMapper: LessonMapper
) {

    suspend fun getLessons(groupName: String): Flow<List<Lesson>> {
        val localData = timetableDao.getGroupWithLessons(groupName)

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

    suspend fun deleteGroupAndLessons(groupName: String) {
        timetableDao.deleteGroupWithLessons(groupName)
    }

    fun getAllGroups(): Flow<List<Group>> = timetableDao.getAllGroups()

    private suspend fun getScheduleFromApi(groupName: String): List<Lesson> {
        val lessonDtoList = apiService.getSchedule(groupName)
        val lessonList = lessonDtoList.map { dto -> lessonMapper.mapDtoToModel(dto) }

        return lessonList
    }

    private suspend fun cacheLessons(groupName: String, lessons: List<Lesson>) {
        val group = Group(groupName)
        val groupWithLessons = GroupWithLessons(group, lessons)

        timetableDao.insertGroupWithLessons(groupWithLessons)
    }

}