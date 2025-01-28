package com.nelsonxilv.gstoutimetable.data.mapper.mappers

import com.nelsonxilv.gstoutimetable.data.model.LessonDbModel
import com.nelsonxilv.gstoutimetable.data.network.model.DisciplineDto
import com.nelsonxilv.gstoutimetable.data.network.model.GroupDto
import com.nelsonxilv.gstoutimetable.data.network.model.LessonDto
import com.nelsonxilv.gstoutimetable.domain.entity.TimeInterval
import javax.inject.Inject

class LessonDtoMapper @Inject constructor(
    private val periodToTimeMapper: PeriodToTimeMapper
) : Mapper<LessonDto, LessonDbModel> {

    override fun transform(data: LessonDto) = LessonDbModel(
        lessonId = data.id ?: NO_DATA_VALUE,
        name = data.disciplineDto?.name ?: "No discipline",
        teacher = getTeacherName(data.activityType, data.disciplineDto),
        auditorium = getAuditoriumName(data.auditoriumDto?.name),
        groupNames = mapGroupsDtoToString(data.groupsDto),
        timeInterval = calculatePeriodTimeInterval(data.period, data.groupsDto),
        activityType = getActivityTypeString(data.activityType),
        period = data.period,
        dayOfWeek = data.weekDay ?: NO_DATA_VALUE,
        week = data.week ?: NO_DATA_VALUE,
        subgroupNumber = data.subgroupNumber ?: NO_DATA_VALUE
    )

    private fun getTeacherName(activityType: Int?, disciplineDto: DisciplineDto?): String {
        val teacherName = when (activityType) {
            LECTURE -> disciplineDto?.lectureTeacherDto?.name
            LAB -> disciplineDto?.labTeacherDto?.name
            PRACTICE -> disciplineDto?.practiceTeacherDto?.name
            else -> "No teacher"
        }

        return formatName(teacherName)
    }

    private fun getAuditoriumName(name: String?) = if (name.isNullOrBlank()) "No room" else name

    private fun mapGroupsDtoToString(dtoList: List<GroupDto>?): List<String> =
        dtoList?.mapNotNull { it.name } ?: emptyList()

    private fun calculatePeriodTimeInterval(
        period: Int,
        listGroups: List<GroupDto>?
    ): TimeInterval {
        val isFSPO = listGroups?.all { group ->
            group.instituteDto?.name == FSPO
        } ?: false

        return periodToTimeMapper.getPeriodTime(period, isFSPO)
    }

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

        val parts = fullName.trim().split(SPACE).filter { it.isNotEmpty() }
        return if (parts.size > 1) {
            parts[0] + SPACE + parts.drop(1).joinToString(SPACE) { "${it[0]}." }
        } else {
            fullName
        }
    }

    companion object {
        private const val LECTURE = 1
        private const val LAB = 2
        private const val PRACTICE = 3
        private const val FSPO = "ФСПО"
        private const val NO_DATA_VALUE = 0
        private const val SPACE = " "
    }

}