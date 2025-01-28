package com.nelsonxilv.gstoutimetable.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuditoriumDto(
    @SerialName("name") val name: String?,
)