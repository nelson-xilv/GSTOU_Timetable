package com.nelsonxilv.gstoutimetable.data.mapper

import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.data.network.model.DisciplineDto
import com.nelsonxilv.gstoutimetable.data.network.model.GroupDto
import com.nelsonxilv.gstoutimetable.data.network.model.LessonDto
import javax.inject.Inject

class LessonMapper @Inject constructor(
    private val periodToTimeMapper: PeriodToTimeMapper
) {

    fun mapDtoToModel(dto: LessonDto) =
        Lesson(
            lessonId = dto.id ?: 0,
            name = dto.disciplineDto?.name ?: "No discipline",
            teacher = getTeacherName(dto.activityType, dto.disciplineDto),
            auditorium = dto.auditoriumDto?.name ?: "No room",
            groupsNames = mapGroupsDtoToString(dto.groupsDto),
            timeInterval = periodToTimeMapper.getPeriodTime(dto.period),
            activityType = getActivityTypeString(dto.activityType),
            period = dto.period,
            dayOfWeek = dto.weekDay ?: 0,
            week = dto.week ?: 0,
            subgroupNumber = dto.groupNumber ?: 0
        )

    private fun getTeacherName(activityType: Int?, disciplineDto: DisciplineDto?) =
        when (activityType) {
            LECTURE -> formatName(disciplineDto?.lectureTeacherDto?.name)
            LAB -> formatName(disciplineDto?.labTeacherDto?.name)
            PRACTICE -> formatName(disciplineDto?.practiceTeacherDto?.name)
            else -> "No teacher"
        }

    private fun mapGroupsDtoToString(dtoList: List<GroupDto>?) =
        dtoList?.map { group ->
            group.name ?: "Unknown Group"
        } ?: emptyList()

    private fun getActivityTypeString(activityType: Int?) =
        when (activityType) {
            LECTURE -> "Лек."
            LAB -> "Лаб."
            PRACTICE -> "Пр."
            else -> "Unknown"
        }

    private fun formatName(fullName: String?): String {
        if (fullName == null) {
            return "No teacher"
        }

        val parts = fullName.split(" ")
        return if (parts.size > 1) {
            parts[0] + " " + parts.drop(1).joinToString(" ") { "${it[0]}." }
        } else {
            fullName
        }
    }

    companion object {
        private const val LECTURE = 1
        private const val LAB = 2
        private const val PRACTICE = 3
    }

}