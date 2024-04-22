package com.nelsonxilv.gstoutimetable.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Institute(
    val id: Int,
    val name: String
)