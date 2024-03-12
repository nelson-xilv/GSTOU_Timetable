package com.nelsonxilv.gstoutimetable.domain.repository

import com.nelsonxilv.gstoutimetable.domain.model.DaySchedule
import com.nelsonxilv.gstoutimetable.domain.model.Group

interface TimetableRepository {

    fun addGroup(group: Group)

    fun changeGroup(group: Group)

    fun getScheduleForDay(dayName: String): DaySchedule

    fun getScheduleForWeek(): List<DaySchedule>

}