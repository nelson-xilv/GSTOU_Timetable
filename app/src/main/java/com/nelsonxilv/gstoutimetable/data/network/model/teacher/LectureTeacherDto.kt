package com.nelsonxilv.gstoutimetable.data.network.model.teacher

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LectureTeacherDto(
    @SerialName("name") val name: String?,
)