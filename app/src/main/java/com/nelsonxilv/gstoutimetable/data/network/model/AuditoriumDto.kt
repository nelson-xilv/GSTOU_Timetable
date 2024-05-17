package com.nelsonxilv.gstoutimetable.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AuditoriumDto(
    val id: Int?,
    val name: String?,
    /**
     * [capacity] содержит в себе значение,
     * которое отображает вмещаемость данной аудитории.
     */
    val capacity: Int?,
    /**
     * [variant] - тип аудитории.
     */
    val variant: String?
)