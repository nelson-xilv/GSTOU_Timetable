package com.nelsonxilv.gstoutimetable.data

import com.nelsonxilv.gstoutimetable.data.entity.Lesson
import com.nelsonxilv.gstoutimetable.data.network.TimetableApiFactory

class TimetableRepository {

    suspend fun getSchedule(): List<Lesson> {
        return TimetableApiFactory.retrofitService.getSchedule()
    }
}