package com.nelsonxilv.gstoutimetable.domain.model

data class DaySchedule(
    val name: String,
    val disciplineList: List<Discipline>
)