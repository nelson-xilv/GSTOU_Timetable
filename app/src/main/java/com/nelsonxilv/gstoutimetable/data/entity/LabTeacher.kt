package com.nelsonxilv.gstoutimetable.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LabTeacher(
    val id: Int?,
    val name: String?,
    @SerialName("user_id")
    val userId: Int?
)