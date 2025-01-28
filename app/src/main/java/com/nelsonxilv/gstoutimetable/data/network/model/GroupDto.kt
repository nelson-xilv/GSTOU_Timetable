package com.nelsonxilv.gstoutimetable.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing a group.
 * This class is used to transfer group information between different parts of the application
 * or over a network.
 *
 * @property name A short name for the group.
 * @property instituteDto An [InstituteDto] object representing the institute to which the group belongs. Can be null if the group isn't associated with a particular institute.
 */
@Serializable
data class GroupDto(
    @SerialName("name") val name: String?,
    @SerialName("institute") val instituteDto: InstituteDto?,
)