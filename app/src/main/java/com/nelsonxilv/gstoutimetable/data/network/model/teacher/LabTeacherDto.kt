package com.nelsonxilv.gstoutimetable.data.network.model.teacher

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LabTeacherDto(
    val id: Int?,
    val name: String?,
    @SerialName("user_id")
    val userId: Int?
)