package com.nelsonxilv.gstoutimetable.domain.entity

data class Group(
    val id: Int?,
    val name: String?,
    val direction: String?,
    val trainingForm: String?,
    val institute: Institute?
)