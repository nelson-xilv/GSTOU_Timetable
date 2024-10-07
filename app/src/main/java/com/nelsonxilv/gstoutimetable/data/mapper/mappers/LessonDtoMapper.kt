package com.nelsonxilv.gstoutimetable.data.mapper.mappers

import com.nelsonxilv.gstoutimetable.data.model.LessonDbModel
import com.nelsonxilv.gstoutimetable.data.network.model.DisciplineDto
import com.nelsonxilv.gstoutimetable.data.network.model.GroupDto
import com.nelsonxilv.gstoutimetable.data.network.model.LessonDto
import javax.inject.Inject

class LessonDtoMapper @Inject constructor(
    private val periodToTimeMapper: PeriodToTimeMapper
) : Mapper<LessonDto, LessonDbModel> {

    override fun transform(data: LessonDto) = LessonDbModel(
        lessonId = data.id ?: 0,
        name = data.disciplineDto?.name ?: "No discipline",
        teacher = getTeacherName(data.activityType, data.disciplineDto),
        auditorium = getAuditoriumName(data.auditoriumDto?.name),
        groupNames = mapGroupsDtoToString(data.groupsDto),
        timeInterval = periodToTimeMapper.getPeriodTime(data.period),
        activityType = getActivityTypeString(data.activityType),
        period = data.period,
        dayOfWeek = data.weekDay ?: 0,
        week = data.week ?: 0,
        subgroupNumber = data.groupNumber ?: 0
    )

    private fun getAuditoriumName(name: String?): String {
        if (name.isNullOrEmpty() || name.isBlank()) {
            return "No room"
        }

        return name
    }

    private fun getTeacherName(activityType: Int?, disciplineDto: DisciplineDto?) =
        when (activityType) {
            LECTURE -> formatName(disciplineDto?.lectureTeacherDto?.name)
            LAB -> formatName(disciplineDto?.labTeacherDto?.name)
            PRACTICE -> formatName(disciplineDto?.practiceTeacherDto?.name)
            else -> "No teacher"
        }

    private fun mapGroupsDtoToString(dtoList: List<GroupDto>?) = dtoList?.map { group ->
        group.name ?: "Unknown Group"
    } ?: emptyList()

    private fun getActivityTypeString(activityType: Int?) = when (activityType) {
        LECTURE -> "Лек."
        LAB -> "Лаб."
        PRACTICE -> "Пр."
        else -> "Unknown"
    }

    private fun formatName(fullName: String?): String {
        if (fullName.isNullOrBlank()) {
            return "No teacher"
        }

        val parts = fullName.trim().split(" ").filter { it.isNotEmpty() }
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