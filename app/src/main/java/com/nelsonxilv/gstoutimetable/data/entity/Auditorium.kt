package com.nelsonxilv.gstoutimetable.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Auditorium(
    val id: Int,
    val name: String,
    /**
     * [capacity] содержит в себе значение,
     * которое отображает вмещаемость данной аудитории.
     */
    val capacity: Int,
    /**
     * [variant] - тип аудитории.
     */
    val variant: String
)