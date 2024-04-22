package com.nelsonxilv.gstoutimetable.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val id: Int,
    /**
     * [name] - краткое название группы.
     */
    val name: String,
    /**
     * [direction] - полное название группы.
     */
    val direction: String,
    /**
     * [trainingForm] обычно содержит в себе формат обучения группы:
     * очно, заочно, очно-заочно.
     */
    @SerialName("training_form")
    val trainingForm: String,
    val institute: Institute
)