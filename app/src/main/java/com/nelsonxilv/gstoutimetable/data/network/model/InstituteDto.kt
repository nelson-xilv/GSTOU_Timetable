package com.nelsonxilv.gstoutimetable.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class InstituteDto(
    val id: Int?,
    val name: String?
)