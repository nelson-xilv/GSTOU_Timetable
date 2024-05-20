package com.nelsonxilv.gstoutimetable.data

import android.util.Log
import com.nelsonxilv.gstoutimetable.data.mapper.LessonMapper
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.data.network.TimetableApiFactory

class TimetableRepository {

    private val lessonMapper = LessonMapper()

    suspend fun getSchedule(): List<Lesson> {
        val lessonDtoList = TimetableApiFactory.retrofitService.getSchedule()
        Log.d(TAG, "Lesson DTO list: $lessonDtoList")

        val lessonList = lessonDtoList.map { dto -> lessonMapper.mapDtoToModel(dto) }
        Log.d(TAG, "Lesson list: $lessonList")

        return lessonList
    }

    companion object {
        private const val TAG = "TimetableRepository"
    }
}