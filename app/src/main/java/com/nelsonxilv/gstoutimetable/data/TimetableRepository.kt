package com.nelsonxilv.gstoutimetable.data

import com.nelsonxilv.gstoutimetable.data.network.entity.LessonDto
import com.nelsonxilv.gstoutimetable.data.network.TimetableApiFactory

class TimetableRepository {

    suspend fun getSchedule(): List<LessonDto> {
        return TimetableApiFactory.retrofitService.getSchedule()
    }
}