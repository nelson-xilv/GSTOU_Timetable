package com.nelsonxilv.gstoutimetable.data.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LectureTeacherDto(
    val id: Int?,
    val name: String?,
    @SerialName("user_id")
    val userId: Int?
)