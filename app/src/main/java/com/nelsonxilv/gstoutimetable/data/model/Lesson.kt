package com.nelsonxilv.gstoutimetable.data.model

data class Lesson(
    val name: String,
    val teacher: String,
    val auditorium: String,
    val groups: List<String>,
    val timeInterval: TimeInterval,
    val activityType: String,
    val period: Int,
    val dayOfWeek: Int,
    val week: Int
)
