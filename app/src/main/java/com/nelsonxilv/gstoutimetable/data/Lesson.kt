package com.nelsonxilv.gstoutimetable.data

data class Lesson(
    val name: String,
    val teacher: String,
    val room: String,
    val groups: List<String>,
    val activityType: String
)
