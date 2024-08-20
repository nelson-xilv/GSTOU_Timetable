package com.nelsonxilv.gstoutimetable.data

import com.nelsonxilv.gstoutimetable.data.database.GroupWithLessons
import com.nelsonxilv.gstoutimetable.data.database.TimetableDao
import com.nelsonxilv.gstoutimetable.data.mapper.MapperService
import com.nelsonxilv.gstoutimetable.data.model.GroupDbModel
import com.nelsonxilv.gstoutimetable.data.model.LessonDbModel
import com.nelsonxilv.gstoutimetable.data.network.TimetableApiService
import com.nelsonxilv.gstoutimetable.domain.TimetableRepository
import com.nelsonxilv.gstoutimetable.domain.entity.Group
import com.nelsonxilv.gstoutimetable.domain.entity.Lesson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TimetableRepositoryImpl @Inject constructor(
    private val apiService: TimetableApiService,
    private val timetableDao: TimetableDao,
    private val mapper: MapperService
) : TimetableRepository {

    override suspend fun getLessonList(groupName: String): Flow<List<Lesson>> {
        val localData = timetableDao.getGroupWithLessons(groupName)

        return flow {
            if (localData == null) {
                val lessonDbModels = getScheduleFromApi(groupName)
                cacheLessons(groupName, lessonDbModels)
                val lessons = mapper.mapListLessonDbToListEntity(lessonDbModels)
                emit(lessons)
            } else {
                val lessons = mapper.mapListLessonDbToListEntity(localData.lessons)
                emit(lessons)
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteGroupAndLessons(groupName: String) {
        timetableDao.deleteGroupWithLessons(groupName)
    }

    override fun getGroupList(): Flow<List<Group>> =
        timetableDao.getAllGroups().map { listGroupDb ->
            mapper.mapListGroupDbToListEntity(listGroupDb)
        }

    private suspend fun getScheduleFromApi(groupName: String): List<LessonDbModel> {
        val lessonDtoList = apiService.getSchedule(groupName)
        val lessonDbList = mapper.mapListLessonDtoToListDbModel(lessonDtoList)

        return lessonDbList
    }

    private suspend fun cacheLessons(groupName: String, lessonDbModels: List<LessonDbModel>) {
        val group = GroupDbModel(groupName)
        val groupWithLessons = GroupWithLessons(group, lessonDbModels)

        timetableDao.insertGroupWithLessons(groupWithLessons)
    }

}