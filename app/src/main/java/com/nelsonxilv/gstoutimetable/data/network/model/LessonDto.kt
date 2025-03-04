package com.nelsonxilv.gstoutimetable.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing a lesson in a schedule.
 *
 * This class encapsulates the information about a specific lesson (class/session)
 * within a timetable. It's designed for data transfer and serialization.
 *
 * @property id The unique identifier of the lesson. Can be null if the lesson is not yet persisted.
 * @property activityType The type of activity for the lesson.
 *   - `1`: Lecture
 *   - `2`: Lab
 *   - `3`: Practice
 * @property auditoriumDto Details about the auditorium where the lesson takes place. Can be null if auditorium is not assigned yet.
 * @property disciplineDto Details about the subject/discipline of the lesson. Can be null if discipline is not assigned yet.
 * @property subgroupNumber The subgroup number for the lesson. `null` indicates the lesson is for the entire group.
 * @property groupsDto The list of groups for which the lesson is scheduled. Can be null or empty if groups are not defined yet.
 * @property period The sequential order of the lesson within a day (e.g., 1st, 2nd, 3rd period).
 * @property week The week number when the lesson occurs. `null` if `duration` is `2` (occurs on both weeks), otherwise 1 or 2.
 * @property weekDay The day of the week for the lesson (represented as an integer, e.g., Monday=1, Tuesday=2, etc.).
 */
@Serializable
data class LessonDto(
    @SerialName("id") val id: Int?,
    @SerialName("activity_type") val activityType: Int?,
    @SerialName("auditorium") val auditoriumDto: AuditoriumDto?,
    @SerialName("discipline") val disciplineDto: DisciplineDto?,
    @SerialName("group_number") val subgroupNumber: Int?,
    @SerialName("groups") val groupsDto: List<GroupDto>?,
    @SerialName("period") val period: Int,
    @SerialName("week") val week: Int?,
    @SerialName("week_day") val weekDay: Int?,
)