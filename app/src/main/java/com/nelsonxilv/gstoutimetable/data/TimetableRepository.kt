package com.nelsonxilv.gstoutimetable.data

import android.util.Log
import com.nelsonxilv.gstoutimetable.data.mapper.LessonMapper
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.data.network.TimetableApiFactory
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class TimetableRepository {

    private val lessonMapper = LessonMapper()

    suspend fun getTodaySchedule(): List<Lesson> {
        val allLesson = getSchedule()
        val dayOfWeek = LocalDate.now().dayOfWeek

        val dayOfWeekNumber = dayOfWeek.get(WeekFields.of(Locale.getDefault()).dayOfWeek())
        val lessonList = allLesson.filter { lesson -> lesson.dayOfWeek == dayOfWeekNumber }

        Log.d(TAG, "Day of week: $dayOfWeek\n" +
                "Number day of week: $dayOfWeekNumber\n" +
                "Lesson list: $lessonList")

        return lessonList
    }

    private suspend fun getSchedule(): List<Lesson> {
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