package com.nelsonxilv.gstoutimetable.data

import android.util.Log
import com.nelsonxilv.gstoutimetable.data.mapper.LessonMapper
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.data.network.TimetableApiService
import javax.inject.Inject

class TimetableRepository @Inject constructor(
    private val retrofitService: TimetableApiService,
    private val lessonMapper: LessonMapper
) {

    suspend fun getSchedule(groupName: String): List<Lesson> {
        val lessonDtoList = retrofitService.getSchedule(groupName)
        Log.d(TAG, "Lesson DTO list: $lessonDtoList")

        val lessonList = lessonDtoList.map { dto -> lessonMapper.mapDtoToModel(dto) }
        Log.d(TAG, "Lesson list: $lessonList")

        return lessonList
    }

    companion object {
        private const val TAG = "TimetableRepository"
    }
}