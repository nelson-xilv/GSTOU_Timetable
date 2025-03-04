package com.nelsonxilv.gstoutimetable.data.network.model.teacher

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LabTeacherDto(
    @SerialName("name") val name: String?,
)