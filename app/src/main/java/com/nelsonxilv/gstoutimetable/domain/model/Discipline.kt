package com.nelsonxilv.gstoutimetable.domain.model

data class Discipline(
    val name: String,
    val type: String,
    val lecturer: String,
    val group: Group,
    val auditorium: String
)
