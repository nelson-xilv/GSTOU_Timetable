package com.nelsonxilv.gstoutimetable.data.mapper

import android.util.Log
import com.nelsonxilv.gstoutimetable.data.model.Lesson
import com.nelsonxilv.gstoutimetable.data.model.TimeInterval
import com.nelsonxilv.gstoutimetable.data.network.model.GroupDto
import com.nelsonxilv.gstoutimetable.data.network.model.LessonDto
import javax.inject.Inject

class LessonMapper @Inject constructor() {

    private val periodToTime = mapOf(
        1 to TimeInterval("9:00", "10:20"),
        2 to TimeInterval("10:30", "11:50"),
        3 to TimeInterval("13:00", "14:20"),
        4 to TimeInterval("14:30", "15:50"),
        5 to TimeInterval("16:00", "17:20"),
        6 to TimeInterval("17:30", "18:50"),
        7 to TimeInterval("19:00", "20:20"),
        8 to TimeInterval("20:30", "21:50")
    )

    fun mapDtoToModel(dto: LessonDto): Lesson {
        Log.d(TAG, "LessonDto: $dto")
        val lesson = Lesson(
            name = dto.disciplineDto?.name ?: "No discipline",
            teacher = getTeacherName(dto),
            auditorium = dto.auditoriumDto?.name ?: "No room",
            groups = mapGroupsDtoToString(dto.groupsDto),
            timeInterval = getPeriodTime(dto),
            activityType = getActivityTypeString(dto) ?: "Unknown",
            period = dto.period,
            dayOfWeek = dto.weekDay ?: 0,
            week = dto.week ?: 0,
            subgroupNumber = dto.groupNumber ?: 0
        )
        Log.d(TAG, "Lesson: $lesson")

        return lesson
    }

    private fun getTeacherName(dto: LessonDto): String =
        when (dto.activityType) {
            1 -> formatName(dto.disciplineDto?.lectureTeacherDto?.name)
            2 -> formatName(dto.disciplineDto?.labTeacherDto?.name)
            3 -> formatName(dto.disciplineDto?.practiceTeacherDto?.name)
            else -> "No teacher"
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

    private fun mapGroupsDtoToString(dtoList: List<GroupDto>?): List<String> {
        return dtoList?.map { group ->
            group.name ?: "Unknown Group"
        } ?: emptyList()
    }

    private fun getPeriodTime(dto: LessonDto): TimeInterval {
        return periodToTime[dto.period] ?: TimeInterval("00:00", "00:00")
    }

    private fun getActivityTypeString(dto: LessonDto): String? {
        return when (dto.activityType) {
            LECTURE -> "Лек."
            LAB -> "Лаб."
            PRACTICE -> "Пр."
            else -> null
        }
    }

    companion object {
        private const val LECTURE = 1
        private const val LAB = 2
        private const val PRACTICE = 3

        private const val TAG = "LessonMapper"
    }
}