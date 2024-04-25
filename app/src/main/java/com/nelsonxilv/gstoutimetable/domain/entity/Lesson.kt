package com.nelsonxilv.gstoutimetable.domain.entity

data class Lesson(
    val id: Int?,
    val activityType: Int?,
    val auditorium: Auditorium?,
    val discipline: Discipline?,
    val duration: Int?,
    val groupNumber: Int?,
    val groups: List<Group>?,
    val isSplit: Boolean?,
    val period: Int?,
    val week: Int?,
    val weekDay: Int?
)