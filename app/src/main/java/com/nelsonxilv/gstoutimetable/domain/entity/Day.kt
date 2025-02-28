package com.nelsonxilv.gstoutimetable.domain.entity

data class Day(
    val name: String,
    val lessons: List<Lesson>
)