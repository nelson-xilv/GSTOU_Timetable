package com.nelsonxilv.gstoutimetable.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
class PracticeTeacher(
    val id: Int?,
    val name: String?,
    @SerialName("user_id")
    val userId: Int?
)